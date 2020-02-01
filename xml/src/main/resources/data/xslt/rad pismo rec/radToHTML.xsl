<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:sc="https://github.com/ZoricBojana/XML-Tim9" version="2.0">
    <xsl:import href="Templates.xsl"/>
    <xsl:template match="/">
        <html>
            <head>
                <title><xsl:value-of select="sc:scientific_article/sc:title"/></title>
            </head>

            <body style="padding-left:10%; padding-right:10%">
                <br/><h1 align="center"><xsl:value-of select="sc:scientific_article/sc:title"/></h1>

                <table width="100%" style="table-layout: fixed">
                    <tr>
                        <xsl:for-each select="sc:scientific_article/sc:authors/sc:author">
                            <td>
                                <div align="center" style="overflow-x:auto">
                                    <xsl:call-template name="Author">
                                        <xsl:with-param name="author" select = "." />
                                    </xsl:call-template>
                                </div>
                            </td>
                        </xsl:for-each>
                    </tr>
                </table><br/><br/>

                <h2 style="margin-left: 10px; margin-bottom: 5px;">Abstract</h2><br/>
                <xsl:for-each select="sc:scientific_article/sc:abstract/sc:abstract-item"><b><xsl:value-of select="@title"/>: </b><xsl:value-of select="."/><br/></xsl:for-each>
                <br/><b>Keywords: </b><xsl:for-each select="sc:scientific_article/sc:metadata/sc:keywords/sc:keyword">
                <xsl:value-of select="."/><xsl:if test="not(position()=last())">,&#160;</xsl:if></xsl:for-each>
                <br/><br/>

                <xsl:for-each select="sc:scientific_article/sc:content/sc:section">
                    <h2 style="margin-left: 10px; margin-bottom: 5px;"><xsl:value-of select="sc:title"/></h2><br/>
                    <xsl:for-each select="./sc:paragraph">
                        <xsl:call-template name="Paragraph"/>
                    </xsl:for-each>
                    <br/><br/>
                </xsl:for-each>

                <h2 style="margin-left: 10px; margin-bottom: 5px;">References</h2><br/>
                <xsl:for-each select="sc:scientific_article/sc:references/sc:reference">
                    <div style="margin:5px">
                        <xsl:for-each select="sc:authors/sc:name">
                            <xsl:if test="position()=last()">and&#160;</xsl:if>
                            <xsl:value-of select="sc:first-name"/>&#160;<xsl:for-each select="sc:middle-name"><xsl:value-of select="."/>&#160;</xsl:for-each>
                            <xsl:value-of select="sc:last-name"/><xsl:if test="not(position()=last())">,&#160;</xsl:if>
                        </xsl:for-each>
                        &#160;(<xsl:value-of select="sc:publication/sc:year"/>).&#160;<xsl:value-of select="sc:publication/sc:title"/>,&#160;<xsl:value-of select="sc:publication/sc:publisher"/>,&#160;
						<xsl:value-of select="sc:publication/sc:place"/><a><xsl:attribute name="href"><xsl:value-of select="sc:publication/sc:url"/></xsl:attribute>[^]</a>
                    </div>
                </xsl:for-each>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>