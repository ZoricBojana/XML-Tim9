<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:msb="http://www.uns.ac.rs/MSB" version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Review (XSLT)</title>
              
            </head>
            <body>
                <h1>Review (XSLT)</h1>
                <p>This is HTML of review!
                 	<b><xsl:value-of select="msb:review/msb:article_info"/></b>
                </p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
