//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.13 at 05:53:01 PM CET 
//


package rs.ac.uns.msb;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rs.ac.uns.msb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NewPage_QNAME = new QName("http://www.uns.ac.rs/MSB", "new_page");
    private final static QName _NewLline_QNAME = new QName("http://www.uns.ac.rs/MSB", "new_lline");
    private final static QName _ReviewCommentsCommentElementId_QNAME = new QName("http://www.uns.ac.rs/MSB", "element_id");
    private final static QName _ReviewSecretCommentsSecretCommentPassword_QNAME = new QName("http://www.uns.ac.rs/MSB", "password");
    private final static QName _ChapterParagraphFormula_QNAME = new QName("http://www.uns.ac.rs/MSB", "formula");
    private final static QName _TgradeGradeValue_QNAME = new QName("http://www.uns.ac.rs/MSB", "grade_value");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.ac.uns.msb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Chapter }
     * 
     */
    public Chapter createChapter() {
        return new Chapter();
    }

    /**
     * Create an instance of {@link ArticleInfo }
     * 
     */
    public ArticleInfo createArticleInfo() {
        return new ArticleInfo();
    }

    /**
     * Create an instance of {@link TreferenceData }
     * 
     */
    public TreferenceData createTreferenceData() {
        return new TreferenceData();
    }

    /**
     * Create an instance of {@link Ref }
     * 
     */
    public Ref createRef() {
        return new Ref();
    }

    /**
     * Create an instance of {@link Paragraph }
     * 
     */
    public Paragraph createParagraph() {
        return new Paragraph();
    }

    /**
     * Create an instance of {@link ChapterParagraph }
     * 
     */
    public ChapterParagraph createChapterParagraph() {
        return new ChapterParagraph();
    }

    /**
     * Create an instance of {@link TformItem }
     * 
     */
    public TformItem createTformItem() {
        return new TformItem();
    }

    /**
     * Create an instance of {@link B }
     * 
     */
    public B createB() {
        return new B();
    }

    /**
     * Create an instance of {@link CoverLetter.Body }
     * 
     */
    public CoverLetter.Body createCoverLetterBody() {
        return new CoverLetter.Body();
    }

    /**
     * Create an instance of {@link List.ListItem }
     * 
     */
    public List.ListItem createListListItem() {
        return new List.ListItem();
    }

    /**
     * Create an instance of {@link Chapter.Header }
     * 
     */
    public Chapter.Header createChapterHeader() {
        return new Chapter.Header();
    }

    /**
     * Create an instance of {@link Review.Comments.Comment }
     * 
     */
    public Review.Comments.Comment createReviewCommentsComment() {
        return new Review.Comments.Comment();
    }

    /**
     * Create an instance of {@link ScientificArticle }
     * 
     */
    public ScientificArticle createScientificArticle() {
        return new ScientificArticle();
    }

    /**
     * Create an instance of {@link ScientificArticle.References.Reference.Type }
     * 
     */
    public ScientificArticle.References.Reference.Type createScientificArticleReferencesReferenceType() {
        return new ScientificArticle.References.Reference.Type();
    }

    /**
     * Create an instance of {@link Review }
     * 
     */
    public Review createReview() {
        return new Review();
    }

    /**
     * Create an instance of {@link I }
     * 
     */
    public I createI() {
        return new I();
    }

    /**
     * Create an instance of {@link ScientificArticle.Authors }
     * 
     */
    public ScientificArticle.Authors createScientificArticleAuthors() {
        return new ScientificArticle.Authors();
    }

    /**
     * Create an instance of {@link ArticleInfo.Title }
     * 
     */
    public ArticleInfo.Title createArticleInfoTitle() {
        return new ArticleInfo.Title();
    }

    /**
     * Create an instance of {@link CoverLetter.Editor }
     * 
     */
    public CoverLetter.Editor createCoverLetterEditor() {
        return new CoverLetter.Editor();
    }

    /**
     * Create an instance of {@link List }
     * 
     */
    public List createList() {
        return new List();
    }

    /**
     * Create an instance of {@link Review.Questionnaire.Item }
     * 
     */
    public Review.Questionnaire.Item createReviewQuestionnaireItem() {
        return new Review.Questionnaire.Item();
    }

    /**
     * Create an instance of {@link Quote }
     * 
     */
    public Quote createQuote() {
        return new Quote();
    }

    /**
     * Create an instance of {@link Review.SecretComments }
     * 
     */
    public Review.SecretComments createReviewSecretComments() {
        return new Review.SecretComments();
    }

    /**
     * Create an instance of {@link ScientificArticle.References.Reference }
     * 
     */
    public ScientificArticle.References.Reference createScientificArticleReferencesReference() {
        return new ScientificArticle.References.Reference();
    }

    /**
     * Create an instance of {@link ScientificArticle.References }
     * 
     */
    public ScientificArticle.References createScientificArticleReferences() {
        return new ScientificArticle.References();
    }

    /**
     * Create an instance of {@link Tgrade }
     * 
     */
    public Tgrade createTgrade() {
        return new Tgrade();
    }

    /**
     * Create an instance of {@link Table.Tr }
     * 
     */
    public Table.Tr createTableTr() {
        return new Table.Tr();
    }

    /**
     * Create an instance of {@link CoverLetter }
     * 
     */
    public CoverLetter createCoverLetter() {
        return new CoverLetter();
    }

    /**
     * Create an instance of {@link Picture }
     * 
     */
    public Picture createPicture() {
        return new Picture();
    }

    /**
     * Create an instance of {@link ScientificArticle.Introduction }
     * 
     */
    public ScientificArticle.Introduction createScientificArticleIntroduction() {
        return new ScientificArticle.Introduction();
    }

    /**
     * Create an instance of {@link ScientificArticle.Conclusion }
     * 
     */
    public ScientificArticle.Conclusion createScientificArticleConclusion() {
        return new ScientificArticle.Conclusion();
    }

    /**
     * Create an instance of {@link Review.Questionnaire }
     * 
     */
    public Review.Questionnaire createReviewQuestionnaire() {
        return new Review.Questionnaire();
    }

    /**
     * Create an instance of {@link Abstract }
     * 
     */
    public Abstract createAbstract() {
        return new Abstract();
    }

    /**
     * Create an instance of {@link ChapterTitle }
     * 
     */
    public ChapterTitle createChapterTitle() {
        return new ChapterTitle();
    }

    /**
     * Create an instance of {@link Review.Comments }
     * 
     */
    public Review.Comments createReviewComments() {
        return new Review.Comments();
    }

    /**
     * Create an instance of {@link ScientificArticle.KeyWords }
     * 
     */
    public ScientificArticle.KeyWords createScientificArticleKeyWords() {
        return new ScientificArticle.KeyWords();
    }

    /**
     * Create an instance of {@link Chapter.Footer }
     * 
     */
    public Chapter.Footer createChapterFooter() {
        return new Chapter.Footer();
    }

    /**
     * Create an instance of {@link ScientificArticle.Chapters }
     * 
     */
    public ScientificArticle.Chapters createScientificArticleChapters() {
        return new ScientificArticle.Chapters();
    }

    /**
     * Create an instance of {@link U }
     * 
     */
    public U createU() {
        return new U();
    }

    /**
     * Create an instance of {@link Institution }
     * 
     */
    public Institution createInstitution() {
        return new Institution();
    }

    /**
     * Create an instance of {@link Review.SecretComments.SecretComment }
     * 
     */
    public Review.SecretComments.SecretComment createReviewSecretCommentsSecretComment() {
        return new Review.SecretComments.SecretComment();
    }

    /**
     * Create an instance of {@link Table }
     * 
     */
    public Table createTable() {
        return new Table();
    }

    /**
     * Create an instance of {@link Author }
     * 
     */
    public Author createAuthor() {
        return new Author();
    }

    public Person createPerson() {
    	return new Person();
    }
    
    public Persons createPersons() {
    	return new Persons();
    }
    /**
     * Create an instance of {@link TreferenceData.Authors }
     * 
     */
    public TreferenceData.Authors createTreferenceDataAuthors() {
        return new TreferenceData.Authors();
    }

    /**
     * Create an instance of {@link TreferenceData.Authors.AuthorOfWork }
     * 
     */
    public TreferenceData.Authors.AuthorOfWork createTreferenceDataAuthorsAuthorOfWork() {
        return new TreferenceData.Authors.AuthorOfWork();
    }

    /**
     * Create an instance of {@link Review.ReviewForm }
     * 
     */
    public Review.ReviewForm createReviewReviewForm() {
        return new Review.ReviewForm();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.uns.ac.rs/MSB", name = "new_page")
    public JAXBElement<String> createNewPage(String value) {
        return new JAXBElement<String>(_NewPage_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.uns.ac.rs/MSB", name = "new_lline")
    public JAXBElement<String> createNewLline(String value) {
        return new JAXBElement<String>(_NewLline_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.uns.ac.rs/MSB", name = "element_id", scope = Review.Comments.Comment.class)
    @XmlIDREF
    public JAXBElement<Object> createReviewCommentsCommentElementId(Object value) {
        return new JAXBElement<Object>(_ReviewCommentsCommentElementId_QNAME, Object.class, Review.Comments.Comment.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.uns.ac.rs/MSB", name = "password", scope = Review.SecretComments.SecretComment.class)
    public JAXBElement<String> createReviewSecretCommentsSecretCommentPassword(String value) {
        return new JAXBElement<String>(_ReviewSecretCommentsSecretCommentPassword_QNAME, String.class, Review.SecretComments.SecretComment.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.uns.ac.rs/MSB", name = "formula", scope = ChapterParagraph.class)
    public JAXBElement<Object> createChapterParagraphFormula(Object value) {
        return new JAXBElement<Object>(_ChapterParagraphFormula_QNAME, Object.class, ChapterParagraph.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.uns.ac.rs/MSB", name = "grade_value", scope = Tgrade.class)
    public JAXBElement<BigInteger> createTgradeGradeValue(BigInteger value) {
        return new JAXBElement<BigInteger>(_TgradeGradeValue_QNAME, BigInteger.class, Tgrade.class, value);
    }

}
