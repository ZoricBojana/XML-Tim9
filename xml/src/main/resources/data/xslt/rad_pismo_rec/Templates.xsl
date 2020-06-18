<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:sc="https://github.com/ZoricBojana/XML-Tim9" version="2.0">

    <xsl:template name="Author">
        <xsl:param name="author"/>
        <div>
            <xsl:value-of select="$author/sc:name/sc:first-name"/>&#160;<xsl:for-each select="$author/sc:name/sc:middle-name"><xsl:value-of select="."/>&#160;</xsl:for-each>
            <xsl:value-of select="$author/sc:name/sc:last-name"/><br/>
            <xsl:value-of select="$author/sc:affiliation/sc:university"/><br/>
            <xsl:value-of select="$author/sc:affiliation/sc:city"/>, <xsl:value-of select="$author/sc:affiliation/sc:state"/>, <xsl:value-of select="$author/sc:affiliation/sc:country"/><br/>
            <xsl:value-of select="$author/sc:email"/>
        </div>
    </xsl:template>

    <xsl:template name="Comment">
        <xsl:param name="comment"/>
        <div>
            <p><xsl:value-of select="$comment/sc:review-reference"/></p>
            <p><xsl:value-of select="$comment/sc:comment-text"/></p>
            <br/>
        </div>
    </xsl:template>

    <xsl:template match="sc:question" name="Question">
        <xsl:if test="@type = 'text'">
            <xsl:for-each select="./*">
                <xsl:if test="name(.) = 'question-text'">
                    <p style="margin-bottom:10px"><xsl:value-of select="."/></p>
                </xsl:if>
                <xsl:if test="name(.) = 'answer'">
                    <p style="margin-top: 5px"><xsl:value-of select="."/></p>
                </xsl:if>
            </xsl:for-each>
        </xsl:if>
        <xsl:if test="@type = 'multiple-choice'">
            <xsl:for-each select="./*">
                <xsl:if test="name(.) = 'question-text'">
                    <p style="margin-bottom: 10px; margin-top: 15px"><xsl:value-of select="."/></p>
                </xsl:if>
                <xsl:if test="name(.) = 'answer'">
                    <xsl:choose>
                        <xsl:when test="@selected = 'true'">
                            <input type="checkbox" name="" value="" checked="true"><xsl:value-of select="."/></input>
                            <br/>
                        </xsl:when>
                        <xsl:otherwise>
                            <input type="checkbox" name="" value=""><xsl:value-of select="."/></input>
                            <br/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>
            </xsl:for-each>

        </xsl:if>
    </xsl:template>

    <xsl:template match="sc:paragraph" name="Paragraph">
        <xsl:for-each select="./*">
            <div style="margin-top: 10px; margin-bottom: 10px">
            <xsl:if test="name(.) = 'quote'">
                <xsl:apply-templates/>
            </xsl:if>

            <xsl:if test="name(.) = 'list'">
                <xsl:choose>
                    <xsl:when test="@ordered='true'">
                        <ol>
                            <xsl:for-each select="./sc:list-item">
                                <li>
                                    <xsl:value-of select="."/>
                                </li>
                            </xsl:for-each>
                        </ol>
                    </xsl:when>
                    <xsl:otherwise>
                        <ul>
                            <xsl:for-each select="./sc:list-item">
                                <li>
                                    <xsl:value-of select="."/>
                                </li>
                            </xsl:for-each>
                        </ul>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
            <xsl:if test="name(.) ='figure'">
                <div>
                    <xsl:for-each select="./sc:image">
                    <img>
                        <xsl:attribute name="src">data:image/*;base64,<xsl:value-of select="."/></xsl:attribute>
                    </img>
                    </xsl:for-each>
                </div>
            </xsl:if>
            <xsl:if test="name(.) ='table'">
                <table border="1">
                    <caption>
                        <xsl:value-of select="@title"/>
                    </caption>
                    <xsl:for-each select="./*">
                        <xsl:choose>
                            <xsl:when test="name(.) = 'header'">
                                <xsl:for-each select="./sc:row">
                                    <tr>
                                        <xsl:for-each select="./sc:column">
                                            <th>
                                                <xsl:value-of select="."/>
                                            </th>
                                        </xsl:for-each>
                                    </tr>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:for-each select="./sc:row">
                                    <tr>
                                        <xsl:for-each select="./sc:column">
                                            <td>
                                                <xsl:value-of select="."/>
                                            </td>
                                        </xsl:for-each>
                                    </tr>
                                </xsl:for-each>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:for-each>
                </table>
            </xsl:if>
            <xsl:if test="name(.) = 'decorator'">
                    <br/><xsl:apply-templates select="."/>
            </xsl:if>
            </div>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="sc:list-item">
        <xsl:for-each select="./*">
            <li>
                <xsl:apply-templates select="."></xsl:apply-templates>
            </li>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="sc:quote/sc:quote-text">
        <q>
            <xsl:value-of select="."/>
        </q>
    </xsl:template>

    <xsl:template match="sc:publication">
        <xsl:value-of select="sc:title" />, <xsl:value-of select="sc:publisher" />, <xsl:value-of select="sc:place" />
    </xsl:template>


    <xsl:template match="sc:bold">
        <b>
            <xsl:for-each select="./* | text()">
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </b>
    </xsl:template>

    <xsl:template match="sc:italic">
        <i>
            <xsl:for-each select="./* | text()">
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </i>
    </xsl:template>

    <xsl:template match="sc:underline">
        <u>
            <xsl:for-each select="./* | text()">
                <xsl:apply-templates select="."></xsl:apply-templates>
            </xsl:for-each>
        </u>
    </xsl:template>

    <xsl:template match="sc:bold/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="sc:italic/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="sc:underline/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="sc:paragraph/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="sc:figure/text()">
        <xsl:copy-of select="." />
    </xsl:template>

    <xsl:template match="sc:quote/text()">
        <xsl:copy-of select="." />
    </xsl:template>


    <xsl:template match="sc:decorator/text()">
        <xsl:copy-of select="." />
    </xsl:template>





</xsl:stylesheet>