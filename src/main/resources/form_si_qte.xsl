<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo">
	<xsl:attribute-set name="tableBorder">
		<xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
	</xsl:attribute-set>
	<xsl:param name="paper-width">
		8.5in
	</xsl:param>
	<xsl:param name="paper-height">
		11in
	</xsl:param>
	<xsl:param name="paper-height-default">
		11in
	</xsl:param>
	<xsl:param name="paper-width-default">
		8.5in
	</xsl:param>
	<xsl:param name="template-company-name">Starr
		Insurance &amp; Reinsurance Limited
	</xsl:param>
	<xsl:template match="POLICY_DATA">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master
					margin="15mm 20mm 10mm 14mm" master-name="PageMaster-Cover">
					<xsl:attribute name="page-height">
                        <xsl:value-of
						select="$paper-height" />
                    </xsl:attribute>
					<xsl:attribute name="page-width">
                        <xsl:value-of
						select="$paper-width" />
                    </xsl:attribute>
					<fo:region-body margin="0mm 0mm 0mm 0mm" />
				</fo:simple-page-master>
				<fo:simple-page-master
					margin="25mm 25mm 25mm 25mm" master-name="PageMaster-TOC">
					<xsl:attribute name="page-height">
                        <xsl:value-of
						select="$paper-height" />
                    </xsl:attribute>
					<xsl:attribute name="page-width">
                        <xsl:value-of
						select="$paper-width" />
                    </xsl:attribute>
					<fo:region-body margin="0mm 0mm 0mm 0mm" />
				</fo:simple-page-master>
				<fo:simple-page-master
					margin="10mm 00mm 10mm 00mm" master-name="PageMaster-Left">
					<xsl:attribute name="page-height">
                        <xsl:value-of
						select="$paper-height-default" />
                    </xsl:attribute>
					<xsl:attribute name="page-width">
                        <xsl:value-of
						select="$paper-width-default" />
                    </xsl:attribute>
					<fo:region-body margin="15mm 25mm 15mm 25mm" />
					<fo:region-before region-name="Left-header"
						extent="10mm" display-align="after" />
					<fo:region-after region-name="Left-footer"
						extent="10mm" display-align="before" />
					<fo:region-start region-name="Left-start"
						extent="20mm" />
					<fo:region-end region-name="Left-end" extent="20mm" />
				</fo:simple-page-master>
				<fo:simple-page-master
					margin="10mm 00mm 10mm 00mm" master-name="PageMaster-Right">
					<xsl:attribute name="page-height">
                        <xsl:value-of
						select="$paper-height-default" />
                    </xsl:attribute>
					<xsl:attribute name="page-width">
                        <xsl:value-of
						select="$paper-width-default" />
                    </xsl:attribute>
					<fo:region-body margin="15mm 25mm 15mm 25mm" />
					<fo:region-before region-name="Right-header"
						extent="10mm" display-align="after" />
					<fo:region-after region-name="Right-footer"
						extent="10mm" display-align="before" />
					<fo:region-start region-name="Right-start"
						extent="20mm" />
					<fo:region-end region-name="Right-end" extent="20mm" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence
				master-reference="PageMaster-Cover">
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						<fo:leader leader-pattern="rule" leader-length="100%"
							rule-style="solid" rule-thickness="1pt" />
						<fo:block>&#xA0;</fo:block>
						<fo:table>
							<fo:table-column column-width="100%" />
							<fo:table-body>
								<xsl:apply-templates select="//ORG_INFO" />
							</fo:table-body>
						</fo:table>
						<fo:leader leader-pattern="rule" leader-length="100%"
							rule-style="solid" rule-thickness="1.5pt" />
					</fo:block>
					<fo:block>&#xA0;</fo:block>
					<fo:block>
						<fo:table>
							<fo:table-column column-width="30%" />
							<fo:table-column column-width="70%" />
							<fo:table-body>
								<xsl:apply-templates
									select="//PRODUCER_FIRM_INFO"
									extension-element-prefixes="#default" />
							</fo:table-body>
						</fo:table>
					</fo:block>
					<fo:block>&#xA0;</fo:block>
					<fo:block>
						<fo:table>
							<fo:table-column column-width="15%" />
							<fo:table-column column-width="85%" />
							<fo:table-body>
								<xsl:apply-templates
									select="//PRODUCER_INFORMATION"
									extension-element-prefixes="#default" />
							</fo:table-body>
						</fo:table>
					</fo:block>
					<fo:block>&#xA0;</fo:block>
					<fo:block font-size="7.5pt" font-family="Arial">
						We are pleased to
						confirm our quotation for the captioned account:
					</fo:block>
					<fo:block>&#xA0;</fo:block>
					<fo:block>
						<fo:table>
							<fo:table-column column-width="15%" />
							<fo:table-column column-width="85%" />
							<fo:table-body>
								<xsl:apply-templates select="//POLICY_QUOTE"
									extension-element-prefixes="#default" />
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="ORG_INFO">
		<fo:table-row width="80%">
			<fo:table-cell text-align="center">
				<fo:block font-size="17.5pt" font-family="Verdana"
					space-after="5mm" font-weight="bold">
					<xsl:value-of select="ORG_NAME" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell text-align="center">
				<fo:block></fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					<xsl:value-of select="ORG_ADDRESS_1" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					<xsl:value-of select="ORG_ADDRESS_2" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell text-align="right">
				<fo:block font-size="7.5pt" font-family="Arial">
					Date :
					<xsl:value-of
						select="format-date(current-date(),'[MNn] [D01], [Y0001]')" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="PRODUCER_FIRM_INFO">
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					<xsl:value-of select="NAME" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					<xsl:value-of select="ADDRESS1" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					<xsl:value-of select="ADDRESS2" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="PRODUCER_INFORMATION">
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					Attn:
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					<xsl:value-of select="PRODUCER_NAME" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					e-Mail:
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					<xsl:value-of select="PRODUCER_EMAIL" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block>&#xA0;</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>&#xA0;</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					RE:
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					Punitive Damages
					Excess liability Insurance Policy
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">

				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					Quote for:Advansix
					Inc
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block>&#xA0;</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>&#xA0;</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					Dear
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>

				<fo:block font-size="7.5pt" font-family="Arial">
					<xsl:value-of select="PRODUCER_GREETING_NAME" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="POLICY_QUOTE">
		<fo:table-row>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial" font-weight="bold">
					Policy Period
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block font-size="7.5pt" font-family="Arial">
					From: 
					<xsl:call-template name="formatdate">
						<xsl:with-param name="DateTimeStr" select="POLICY_QUOTE.EFFECTIVE_DT" />
					</xsl:call-template>
					To: 
					<xsl:call-template name="formatdate">
						<xsl:with-param name="DateTimeStr" select="POLICY_QUOTE.EXPIRATION_DT" />
					</xsl:call-template>
				</fo:block>
				<fo:block font-size="7.5pt" font-family="Arial">
					At 12:01 A.M. standard time at the address of the Named Insured.
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>
	<xsl:template name="formatdate">
		<xsl:param name="DateTimeStr" />

		<xsl:variable name="datestr">
			<xsl:value-of
				select="substring-before($DateTimeStr,' ')" />
		</xsl:variable>

		<xsl:variable name="mm">
			<xsl:value-of select="substring($datestr,6,2)" />
		</xsl:variable>

		<xsl:variable name="dd">
			<xsl:value-of select="substring($datestr,9,2)" />
		</xsl:variable>

		<xsl:variable name="yyyy">
			<xsl:value-of select="substring($datestr,1,4)" />
		</xsl:variable>

		<xsl:value-of select="concat($mm,'/', $dd, '/', $yyyy)" />
	</xsl:template>
</xsl:stylesheet>