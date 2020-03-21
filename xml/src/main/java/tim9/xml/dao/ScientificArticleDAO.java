package tim9.xml.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;

import rs.ac.uns.msb.Author;
import rs.ac.uns.msb.Chapter;
import rs.ac.uns.msb.ChapterParagraph;
import rs.ac.uns.msb.Paragraph;
import rs.ac.uns.msb.Picture;
import rs.ac.uns.msb.ScientificArticle;
import rs.ac.uns.msb.Table;
import tim9.xml.exception.EntityNotFound;
import tim9.xml.util.AuthenticationUtilities;
import tim9.xml.util.NSPrefixMapper;
import tim9.xml.util.AuthenticationUtilities.ConnectionProperties;

public class ScientificArticleDAO {

	// methods: 
		//	-retrieve
		//  -store
		//  -search all published
		// search by title, author, key_word, publisher
	    
		private static ConnectionProperties conn;
		private static final String TARGET_NAMESPACE = "http://www.uns.ac.rs/MSB";
		//private static final String pattern = "<scientific_article>{$article/article_info}{$article/authors}{$article/key_words}</scientific_article>";
		
		public static void main(String[] args) {
			try {
				List<ScientificArticle> articles = ScientificArticleDAO.searchAllPublished("");
				
				for (ScientificArticle scientificArticle : articles) {
					System.out.println(scientificArticle.getArticleInfo().getTitle().getValue());
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMLDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    /**
	     * args[0] Should be the collection ID to access
	     * args[1] Should be the document ID to store in the collection
	     */
	    public static String retrieve(String collectionId, String documentId) throws Exception {
	       
	    	ConnectionProperties conn = AuthenticationUtilities.loadProperties();
	        
	        // initialize database driver
	        Class<?> cl = Class.forName(conn.driver);
	        
	        Database database = (Database) cl.newInstance();
	        database.setProperty("create-database", "true");
	        
	        DatabaseManager.registerDatabase(database);
	        
	        Collection col = null;
	        XMLResource res = null;
	        boolean notFound = false;
	        try {    
	            // get the collection
	            col = DatabaseManager.getCollection(conn.uri + collectionId);
	            col.setProperty(OutputKeys.INDENT, "yes");
	            
	            res = (XMLResource)col.getResource(documentId);
	            
	            if(res == null) {
	                notFound = true;
	            } else {
	            	
	                JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");
	    			
	    			Unmarshaller unmarshaller = context.createUnmarshaller();
	    			
	    			ScientificArticle article = (ScientificArticle) unmarshaller.unmarshal(res.getContentAsDOM());
	    			
	    			if(article == null) {
	    				throw new Exception("Unmarshaling failed");
	    			}
	    			
	    			return (String)res.getContent();
	            }
	        } finally {
	            //don't forget to clean up!
	            
	            if(res != null) {
	                try { 
	                	((EXistResource)res).freeResources(); 
	                } catch (XMLDBException xe) {
	                	xe.printStackTrace();
	                }
	            }
	            
	            if(col != null) {
	                try { 
	                	col.close(); 
	                } catch (XMLDBException xe) {
	                	xe.printStackTrace();
	                }
	            }
	        }
	        
	        if(notFound) {
	        	throw new EntityNotFound(documentId);
	        }
	        return null;
	    }
	    
	    
	    /**
		 * conn XML DB connection properties collectionId Should be the collection ID to
		 * access documentId Should be the document ID to store in the collection
		 * article should be XML article
		 */
		public static void store( String collectionId, String documentId, String articleString)
				throws Exception {
			
			conn = AuthenticationUtilities.loadProperties();

			// initialize database driver
			
			Class<?> cl = Class.forName(conn.driver);

			// encapsulation of the database driver functionality
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");

			// entry point for the API which enables you to get the Collection reference
			DatabaseManager.registerDatabase(database);

			// a collection of Resources stored within an XML database
			Collection col = null;
			XMLResource res = null;
			OutputStream os = new ByteArrayOutputStream();

			try {

				col = getOrCreateCollection(collectionId);

				/*
				 * create new XMLResource with a given id an id is assigned to the new resource
				 * if left empty (null)
				 */
				res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);

				JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");

				Unmarshaller unmarshaller = context.createUnmarshaller();

				StringReader sr = new StringReader(articleString);
				
				ScientificArticle article = (ScientificArticle) unmarshaller.unmarshal(sr);
				
				// ************* setovanje ID-eva *********
				article.setID(documentId);
				article.setPublished(false);
				
				article.getAbstract().setID(documentId + "/abstract");
				int i = 1;
				for (Paragraph par : article.getAbstract().getParagraph()) {
					par.setID(documentId + "/abstract/par" + i);
					int j = 1;
					for (Object o: par.getContent()) {
						if (o instanceof rs.ac.uns.msb.List) {
							((rs.ac.uns.msb.List)o).setID(documentId + "/abstract/par" + i + "/list" + j);
							j++;
						}
					}
					i++;
				}
				
				article.getIntroduction().setID(documentId + "/introduction");
				i = 1;
				for (Paragraph par : article.getIntroduction().getParagraph()) {
					par.setID(documentId + "/introduction/par" + i);
					i++;
					
					int j = 1;
					for (Object o: par.getContent()) {
						if (o instanceof rs.ac.uns.msb.List) {
							((rs.ac.uns.msb.List)o).setID(documentId + "/introduction/par" + i + "/list" + j);
							j++;
						}
					}
				}
				
				i = 1;
				for (Chapter ch : article.getChapters().getChapter()) {
					ch.setID(documentId + "/chapter/" + i);
					i++;
					
					int j = 1;
					for (ChapterParagraph cp : ch.getChapterParagraph()) {
						cp.setID(documentId + "/chapter" + i + "/par" + j);
						j++;
						
						int listNum = 1;
						int tableNum = 1;
						int pictureNum = 1;
						for (Object o: cp.getContent()) {
							if (o instanceof rs.ac.uns.msb.List) {
								((rs.ac.uns.msb.List)o).setID(documentId + "/chapter" + i + "/par" + j + "/list" + listNum);
								listNum++;
							} else if (o instanceof Table) {
								((Table)o).setID(documentId + "/chapter" + i + "/par" + j + "/table" + tableNum);
								tableNum++;
							}else if (o instanceof Picture) {
								((Picture)o).setID(documentId + "/chapter" + i + "/par" + j + "/picture" + pictureNum);
								pictureNum++;
							}
						}

					}
				}
				
				article.getConclusion().setID(documentId + "/conclusion");
				i = 1;
				for (Paragraph par : article.getConclusion().getParagraph()) {
					par.setID(documentId + "/conclusion/par" + i);
					i++;
					
					int j = 1;
					for (Object o: par.getContent()) {
						if (o instanceof rs.ac.uns.msb.List) {
							((rs.ac.uns.msb.List)o).setID(documentId + "/conclusion/par" + i + "/list" + j);
							j++;
						}
					}
				}
				
				// ************* END setovanje ID-eva ********
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

				// marshal the contents to an output stream
				marshaller.marshal(article, os);

				// link the stream to the XML resource
				res.setContent(os);

				col.storeResource(res);

			} finally {

				// don't forget to cleanup
				if (res != null) {
					try {
						((EXistResource) res).freeResources();
					} catch (XMLDBException xe) {
						xe.printStackTrace();
					}
				}

				if (col != null) {
					try {
						col.close();
					} catch (XMLDBException xe) {
						xe.printStackTrace();
					}
				}
			}
		}
		
		// pretraga po TEKSTU
		public static List<ScientificArticle> searchAllPublished(String value) throws Exception{
			
			List<ScientificArticle> articles = new ArrayList<ScientificArticle>();
			
			ConnectionProperties conn = AuthenticationUtilities.loadProperties();
			String collectionId = "/db/sample/scientificArticle";
			
			Class<?> cl = Class.forName(conn.driver);
	        
	        Database database = (Database) cl.newInstance();
	        database.setProperty("create-database", "true");
	        
	        DatabaseManager.registerDatabase(database);
	        
	        Collection col = null;
	        
	        if(value == null) {
	        	value = "";
	        }

	        try { 

	            col = DatabaseManager.getCollection(conn.uri + collectionId);
	        	
	         // get an instance of xquery service
	            XQueryService xqueryService = (XQueryService) col.getService("XQueryService", "1.0");
	            xqueryService.setProperty("indent", "yes");
	            
	            // make the service aware of namespaces, using the default one
	            xqueryService.setNamespace("", TARGET_NAMESPACE);
	         
	            String xqueryExpression = null;
	            
	            if (!value.trim().equals("")) {
	            	xqueryExpression =
		            		"let $col := collection(\"/db/sample/scientificArticle\")\r\n" + 
		            		"for $article in $col//scientific_article\r\n" + 
		            		"where contains(lower-case(string($article)), \""+ value.toLowerCase() +"\")" + 
		            		"and $article[@published='true'] " +
		            		"return $article";
	            }else {
	            	
	            	System.out.println("Prazan");
	            	xqueryExpression =
		            		"let $col := collection(\"/db/sample/scientificArticle\")\r\n" + 
		            		"for $article in $col//scientific_article\r\n" + 
		            		"where $article[@published='true'] " +
		            		"and 2=2 " +
		            		"return $article";
	            }
	            
	         // compile and execute the expression
	            CompiledExpression compiledXquery = xqueryService.compile(xqueryExpression);
	            ResourceSet result = xqueryService.execute(compiledXquery);
	            
	            // handle the results
	            System.out.println("[INFO] Handling the results... ");
	            System.out.println(xqueryExpression);
	            
	            ResourceIterator i = result.getIterator();
	            Resource res = null;
	            
	            while(i.hasMoreResources()) {
	            	try {
	                    res = i.nextResource();
	                    //System.out.println(res.getContent());
	                    
	                    JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");
		    			
		    			Unmarshaller unmarshaller = context.createUnmarshaller();
		    			System.out.println(res.getContent().toString());
		    			
		    			ScientificArticle article = (ScientificArticle) unmarshaller.unmarshal(((XMLResource)res).getContentAsDOM());
		    			
		    			if(article == null) {
		    				throw new Exception("Unmarshaling failed");
		    			}
		    			System.out.println(article.getID() + " id");
		    			articles.add(article);
	                    
	                } finally {
	                    
	                	// don't forget to cleanup resources
	                    try { 
	                    	((EXistResource)res).freeResources(); 
	                    } catch (XMLDBException xe) {
	                    	xe.printStackTrace();
	                    }
	                }
	            }
	            
	        } finally {
	        	
	            // don't forget to cleanup
	            if(col != null) {
	                try { 
	                	col.close();
	                } catch (XMLDBException xe) {
	                	xe.printStackTrace();
	                }
	            }
	        }
			
			return articles;
		}
		
		// pretraga po autoru, kljucnim recima, izdavacu
		public static List<ScientificArticle> searchPublishedByMetadata(String title, String author, String keyWord, String publisher) throws Exception{
			
			List<ScientificArticle> articles = new ArrayList<ScientificArticle>();
			
			ConnectionProperties conn = AuthenticationUtilities.loadProperties();
			String collectionId = "/db/sample/scientificArticle";
			
			Class<?> cl = Class.forName(conn.driver);
	        
	        Database database = (Database) cl.newInstance();
	        database.setProperty("create-database", "true");
	        
	        DatabaseManager.registerDatabase(database);
	        
	        Collection col = null;
	        
	        if(title == null) {
	        	title = "";
	        }
	        
	        if(author == null) {
	        	author = "";
	        }
	        
	        if(keyWord == null) {
	        	keyWord = "";
	        }
	        
	        if(publisher == null) {
	        	publisher = "";
	        }

	        try { 

	            col = DatabaseManager.getCollection(conn.uri + collectionId);
	        	
	         // get an instance of xquery service
	            XQueryService xqueryService = (XQueryService) col.getService("XQueryService", "1.0");
	            xqueryService.setProperty("indent", "yes");
	            
	            // make the service aware of namespaces, using the default one
	            xqueryService.setNamespace("", TARGET_NAMESPACE);
	         
	            String xqueryExpression = null;
	            
	            String condition = "";
	            
	            if(title.trim().equals("") && author.trim().equals("") && keyWord.trim().equals("") && publisher.trim().equals("")) {
	            	condition = "";
	            }else {
	            	condition += "where ";
	            	boolean prev = false;
	            	//"where contains(string($article), \""+ value +"\")" + 
	            	if(!title.trim().equals("")) {
	            		condition += "contains(lower-case(string($article/article_info/title)), \"" + title.toLowerCase() + "\") ";
	            		prev = true;
	            	}
	            	if(!author.trim().equals("")) {
	            		if(prev == true) {
	            			condition += "and ";
	            		}
	            		condition += "contains(lower-case(string($article/authors)), \"" + author.toLowerCase() + "\") ";
	            		prev = true;
	            	} 
	            	if(!keyWord.trim().equals("")) {
	            		if(prev == true) {
	            			condition += "and ";
	            		}
	            		condition += "contains(lower-case(string($article/key_words)), \"" + keyWord.toLowerCase() + "\")";
	            		prev = true;
	            	} 
	            	if(!publisher.trim().equals("")) {
	            		if(prev == true) {
	            			condition += "and ";
	            		}
	            		condition += "contains(lower-case(string($article/article_info/publisher)), \"" + publisher.toLowerCase() + "\")";
	            	}
	            	
	            }
	            
	            System.out.println("Condition " + condition);
	            
            	xqueryExpression =
	            		"let $col := collection(\"/db/sample/scientificArticle\")\r\n" + 
	            		"for $article in $col//scientific_article\r\n" +
	            		condition +
	            		"where $article[@published='true']" +
	            		"return $article";
            
	            
	         // compile and execute the expression
	            CompiledExpression compiledXquery = xqueryService.compile(xqueryExpression);
	            ResourceSet result = xqueryService.execute(compiledXquery);
	            
	            // handle the results
	            System.out.println("[INFO] Handling the results... ");
	            
	            ResourceIterator i = result.getIterator();
	            Resource res = null;
	            
	            while(i.hasMoreResources()) {
	            
	            	try {
	                    res = i.nextResource();
	                    //System.out.println(res.getContent());
	                    
	                    JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");
		    			
		    			Unmarshaller unmarshaller = context.createUnmarshaller();
		    			
		    			ScientificArticle article = (ScientificArticle) unmarshaller.unmarshal(((XMLResource)res).getContentAsDOM());
		    			
		    			if(article == null) {
		    				throw new Exception("Unmarshaling failed");
		    			}
		    			
		    			articles.add(article);
	                    
	                } finally {
	                    
	                	// don't forget to cleanup resources
	                    try { 
	                    	((EXistResource)res).freeResources(); 
	                    } catch (XMLDBException xe) {
	                    	xe.printStackTrace();
	                    }
	                }
	            }
	            
	        } finally {
	        	
	            // don't forget to cleanup
	            if(col != null) {
	                try { 
	                	col.close();
	                } catch (XMLDBException xe) {
	                	xe.printStackTrace();
	                }
	            }
	        }
			
			return articles;
		}

		private static Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
			return getOrCreateCollection(collectionUri, 0);
		}

		private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {

			Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);

			// create the collection if it does not exist
			if (col == null) {

				if (collectionUri.startsWith("/")) {
					collectionUri = collectionUri.substring(1);
				}

				String pathSegments[] = collectionUri.split("/");

				if (pathSegments.length > 0) {
					StringBuilder path = new StringBuilder();

					for (int i = 0; i <= pathSegmentOffset; i++) {
						path.append("/" + pathSegments[i]);
					}

					Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);

					if (startCol == null) {

						// child collection does not exist

						String parentPath = path.substring(0, path.lastIndexOf("/"));
						Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user,
								conn.password);

						CollectionManagementService mgt = (CollectionManagementService) parentCol
								.getService("CollectionManagementService", "1.0");

						col = mgt.createCollection(pathSegments[pathSegmentOffset]);

						col.close();
						parentCol.close();

					} else {
						startCol.close();
					}
				}
				return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
			} else {
				return col;
			}
		}
}
