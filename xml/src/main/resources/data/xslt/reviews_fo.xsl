<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:msb="http://www.uns.ac.rs/MSB" 
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="letter-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="letter-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-indent="10px">
						<xsl:for-each
							select="msb:reviews/msb:review">
							<xsl:value-of select="."></xsl:value-of>
							<fo:block>
							Tehnical grade: 
							<xsl:value-of select="msb:review_form/msb:tehnical_grade/msb:grade_value"></xsl:value-of>
							</fo:block>
							<fo:block>
							Language grade: 
							<xsl:value-of select="msb:review_form/msb:language_grade/msb:grade_value"></xsl:value-of>
							</fo:block>
							<fo:block>
							General impression: 
							<xsl:value-of select="msb:review_form/msb:language_grade/msb:general_impression"></xsl:value-of>
							</fo:block>
							<fo:block>
							Good sides: 
							<xsl:value-of select="msb:review_form/msb:good_sides"></xsl:value-of>
							</fo:block>
							<fo:block>
							Bad sides: 
							<xsl:value-of select="msb:review_form/msb:bad_sides"></xsl:value-of>
							</fo:block>
							
							**********************************
						</xsl:for-each>
					</fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
