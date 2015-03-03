package cn.main.sax;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @ClassName: XmlParse
 * @Description: 解析XML文件，获取package和MainActivity
 * @author tgx
 * @date 2015-3-3 下午4:14:09
 * 
 */
public class XmlParse extends DefaultHandler {
	private static String packageName;
	private ArrayList<String> activityName = new ArrayList<String>();
	private static String mainActivity;
	private static int size;

	// start parse
	public void startDocument() throws SAXException {
//		System.out.println("start parse");
	}

	public void endDocument() throws SAXException {
//		System.out.println("end parse");
	}

	/**
	 * @Title: startDocument
	 * @Description: 开始解析文档
	 * @param qName为元素的名称
	 *            attributes是元素内部的属性
	 * @throws SAXException
	 * @return void
	 * @throws
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {
		// System.out.println(qName + " start");
		if (qName.equals("activity")) {
			if (attributes != null && attributes.getLength() != 0) {
				// System.out.println("attribute:");
				for (int i = 0; i < attributes.getLength(); i++) {
					// System.out.print(attributes.getQName(i) + "=");
					// System.out.print(attributes.getValue(i) + "");
					if (attributes.getQName(i).equals("android:name")) {
						activityName.add(attributes.getValue(i));
					}
				}
				// System.out.println();
			}
		} else {
			if (attributes != null && attributes.getLength() != 0) {
				System.out.println("attribute:");
				for (int i = 0; i < attributes.getLength(); i++) {
					// System.out.print(attributes.getQName(i) + "=");
					// System.out.print(attributes.getValue(i) + " ");
					if (attributes.getQName(i).equals("package")) {
						packageName = attributes.getValue(i);
					}
					if (attributes.getValue(i).equals(
							"android.intent.action.MAIN")) {
						size = activityName.size();
						mainActivity = activityName.get(size - 1);
					}
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// System.out.println(qName + "tagValue:" + tagValue);
		// System.out.println(qName + " end");
	}
	public void characters(char ch[], int start, int length)
			throws SAXException {
		System.out.println("characters");
	}
	public String[] xmlParse(String filePath) {
		File file = new File(filePath);
		String[] xmlMessage = new String[2];
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(file, new XmlParse());
		} catch (Exception e) {
			e.printStackTrace();
		}
		xmlMessage[0] = packageName;
		xmlMessage[1] = mainActivity;
		return xmlMessage;
	}
}
