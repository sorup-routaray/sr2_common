package com.sr2_common.core.utility.test;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLUtilityTest {

	public static void main(String[] args) {
		String str1 = "<ROW><PK_ID>27858964</PK_ID><PARENT_ID>27858928</PARENT_ID><SUBJECT>y_refer</SUBJECT><DELETE_FLAG>0</DELETE_FLAG><EFFECTIVE_DT>2019-05-30T18:38:33.1080000</EFFECTIVE_DT><EXPIRATION_DT>2020-05-28T18:38:33.1080000</EXPIRATION_DT><CREATE_USERID_TX>IP_AGENCY</CREATE_USERID_TX><QUOTE_STATUS_CD>NONSTDA</QUOTE_STATUS_CD><PREM_TERM_AMT>0.00</PREM_TERM_AMT><PRODUCT_VERSION_NO>1</PRODUCT_VERSION_NO><PRODUCT_CD>MotorMax</PRODUCT_CD><PREM_EARNED_AMT>0.00</PREM_EARNED_AMT><PREV_PREM__EARNED_AMT>0.00</PREV_PREM__EARNED_AMT><EAPP_CD>QUOTE_MODEL</EAPP_CD><DISPATCHER_STATUS_CD>INIT</DISPATCHER_STATUS_CD><OPTION_DISP_SEQ_NO>1</OPTION_DISP_SEQ_NO><POLICY_TYPE_CD>MotorMax</POLICY_TYPE_CD><PRODUCT_CATEGORY_CD>MPC</PRODUCT_CATEGORY_CD><MEDIUM_CD>IPO</MEDIUM_CD><SALECH_CD>200</SALECH_CD><BRANCH_CD>SG</BRANCH_CD><INTERMEDIARY_IND>Y</INTERMEDIARY_IND><SOURCE_EAPP>IP</SOURCE_EAPP></ROW>";
		String str2 = "<ROW><PK_ID>27858964</PK_ID><PARENT_ID>27858928</PARENT_ID><SUBJECT>y_1refer</SUBJECT><DELETE_FLAG>0</DELETE_FLAG><EFFECTIVE_DT>2019-05-30T18:38:33.1080000</EFFECTIVE_DT><EXPIRATION_DT>2020-05-28T18:38:33.1080000</EXPIRATION_DT><CREATE_USERID_TX>IP_AGENCY</CREATE_USERID_TX><QUOTE_STATUS_CD>NONSTDA</QUOTE_STATUS_CD><PREM_TERM_AMT>0.00</PREM_TERM_AMT><PRODUCT_VERSION_NO>1</PRODUCT_VERSION_NO><PRODUCT_CD>MotorMax</PRODUCT_CD><PREM_EARNED_AMT>0.00</PREM_EARNED_AMT><PREV_PREM__EARNED_AMT>0.00</PREV_PREM__EARNED_AMT><EAPP_CD>QUOTE_MODEL</EAPP_CD><DISPATCHER_STATUS_CD>INIT</DISPATCHER_STATUS_CD><OPTION_DISP_SEQ_NO>1</OPTION_DISP_SEQ_NO><POLICY_TYPE_CD>MotorMax</POLICY_TYPE_CD><PRODUCT_CATEGORY_CD>MPC</PRODUCT_CATEGORY_CD><MEDIUM_CD>IPO</MEDIUM_CD><SALECH_CD>200</SALECH_CD><BRANCH_CD>SG</BRANCH_CD><INTERMEDIARY_IND>Y</INTERMEDIARY_IND><SOURCE_EAPP>IP</SOURCE_EAPP></ROW>";
		Map<String, String> acceptedValueMap = getElementNameList(str1);
		
		Map<String, String> currentValueMap = getElementNameList(str2);
		Boolean valueEqual = Boolean.TRUE;
		for(Map.Entry<String, String> entrySet : acceptedValueMap.entrySet()) {
			String acceptedValue = entrySet.getValue();
			String columnName = entrySet.getKey();
			String latestValue = currentValueMap.get(columnName);
			valueEqual = isValueEqual(acceptedValue, latestValue);
			if(!valueEqual) {
				System.out.println(columnName + " having different values.");
				break;
			}
		}
		String message = "Accepted value and current value " + (valueEqual ? "matches." : "does not matches");
		System.out.println(message);
	}
	
	private static Map<String, String> getElementNameList(String xmlContent) {
		List<String> columnNameList = new ArrayList<String>();
		
		Document loDocuemnt = convertStringToDocument(xmlContent);
		Element documentElement = loDocuemnt.getDocumentElement();
		childRecusrsion(documentElement, columnNameList);
		Map<String, String> columnNameValueMap = new HashMap<String, String>();
		for (String columnName : columnNameList) {
			NodeList nodeList = documentElement.getElementsByTagName(columnName);
			if(nodeList.item(0) == null) {
				continue;
			}
			String value = nodeList.item(0).getTextContent();
			columnNameValueMap.put(columnName, value);
		}
		return columnNameValueMap;
	}
	
	private static Document convertStringToDocument(final String xmlContent) {
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = documentBuilder.parse(new InputSource(new StringReader(xmlContent)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void childRecusrsion(Node node, List<String> columnNameList) {
        columnNameList.add(node.getNodeName());
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                childRecusrsion(currentNode, columnNameList);
            }
        }
    }
	
	private static Boolean isValueEqual(String value1, String value2) {
		if ((isEmpty(value1) && isEmpty(value2))
				|| (!isEmpty(value1) && !isEmpty(value2) && value1.trim().equalsIgnoreCase(value2.trim()))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private static Boolean isEmpty(String inValue) {
		if(inValue == null || inValue.trim().isEmpty()){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
