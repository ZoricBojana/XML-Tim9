<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:sc="https://github.com/ZoricBojana/XML-Tim9" version="2.0">
    <xsl:import href="Templates.xsl"/>
    <xsl:template match="/">
    <html>

        <head>
            <title>Cover letter</title>
        </head>

        <body style="padding-left:10%; padding-right:10%">
           <div align="left">
               <xsl:value-of select="sc:CoverLetter/sc:cover-letter-metadata/sc:date"></xsl:value-of>
               <xsl:call-template name="Author">
                    <xsl:with-param name="author" select="sc:CoverLetter/sc:cover-letter-metadata/sc:author"/>
                </xsl:call-template>
            </div>

            <div align="center" style="margin-top: 10px; margin-bottom:20px">
                Cover Letter for
                <a><xsl:attribute name="href">
                    http://localhost:8080/putanja za rad/<xsl:value-of select="sc:CoverLetter/sc:cover-letter-metadata/sc:scientific-paper-reference"/>
                </xsl:attribute>
                    Scientific Article
                </a>
            </div>
            <div align="center">
            <xsl:for-each select="sc:CoverLetter/sc:content/sc:paragraph">
                <xsl:call-template name="Paragraph"/>
            </xsl:for-each>

            <img>
                <xsl:attribute name="src">
                    data:image/*;base64,<xsl:value-of select="sc:CoverLetter/sc:content/sc:signature"/>
                </xsl:attribute>
            </img>
                
            </div>

            <div align="left">
                <xsl:call-template name="Author">
                    <xsl:with-param name="author" select="sc:CoverLetter/sc:cover-letter-metadata/sc:editor"/>
                </xsl:call-template>
            </div>

        </body>






    </html>
    </xsl:template>

</xsl:stylesheet>