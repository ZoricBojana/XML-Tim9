<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:msb="http://www.uns.ac.rs/MSB"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master
					master-name="bookstore-page">
					<fo:region-body margin="0.75in" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="bookstore-page">
				<fo:flow flow-name="xsl-region-body">
					<fo:block font-family="sans-serif" font-size="24px"
						font-weight="bold" padding="10px">
						<xsl:value-of
							select="msb:scientific_article/msb:article_info/msb:title" />
					</fo:block>

					<fo:block text-indent="10px">
						Kljucne reci:
						<xsl:for-each
							select="msb:scientific_article/msb:key_words/msb:key_word">
							<xsl:value-of select="."></xsl:value-of>
						</xsl:for-each>
					</fo:block>
					<fo:block text-indent="10px">
						Autori:
						<xsl:for-each
							select="msb:scientific_article/msb:authors/msb:author">
							<xsl:value-of select="msb:first_name"></xsl:value-of>

							<xsl:value-of select="msb:last_name"></xsl:value-of>
						</xsl:for-each>
					</fo:block>
					<fo:block text-indent="10px" space-before="5mm"
						space-after="5mm">
						Apstrakt:
						<xsl:for-each
							select="msb:scientific_article/msb:abstract/msb:paragraph">
							<fo:block>
								<xsl:value-of select="."></xsl:value-of>
							</fo:block>

						</xsl:for-each>
					</fo:block>

					<fo:block text-indent="10px" space-before="5mm"
						space-after="5mm">
						Uvod:
						<xsl:for-each
							select="msb:scientific_article/msb:introduction/msb:paragraph">
							<fo:block>
								<xsl:value-of select="."></xsl:value-of>
							</fo:block>

						</xsl:for-each>
					</fo:block>

					<fo:block text-indent="10px" space-before="5mm"
						space-after="5mm">

						<xsl:for-each
							select="msb:scientific_article/msb:chapters/msb:chapter">
							<xsl:value-of select="msb:title"></xsl:value-of>
							<fo:block>
								<xsl:for-each select="msb:chapter_paragraph">
									<fo:block>
										<xsl:value-of select="."></xsl:value-of>
									</fo:block>
								</xsl:for-each>
							</fo:block>

						</xsl:for-each>
					</fo:block>

					<fo:block text-indent="10px" space-before="5mm"
						space-after="5mm">
						Zakljucak:
						<xsl:for-each
							select="msb:scientific_article/msb:conclusion/msb:paragraph">
							<fo:block>
								<xsl:value-of select="."></xsl:value-of>
							</fo:block>

						</xsl:for-each>
					</fo:block>

				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="description">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="b">
		<fo:inline font-weight="bold">
			<xsl:apply-templates />
		</fo:inline>
	</xsl:template>
</xsl:stylesheet>
