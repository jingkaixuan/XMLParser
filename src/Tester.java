import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Tester {
	public static void main(String[] args) {
		List<Device> cameraList = Tester.parseFromFile("data.xml");
		System.out.println(cameraList.size());
	}

	public static List<Device> parseFromFile(String xmlFile) {
		File file = new File(xmlFile);
		BufferedReader br = null;

		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);

			StringBuilder sb = new StringBuilder();
			String s = "";

			while ((s = br.readLine()) != null) {
				sb.append(s + "\n");
			}

			return Tester.domXml(sb.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/** 解析本地xml(响应) 文件 格式 见定义 */
	public static List<Device> domXml(String xmlContent) {
		List<Device> cameras = new ArrayList<Device>();
		Element element = null;
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = null;
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();

			StringReader sr = new StringReader(xmlContent);
			InputSource is = new InputSource(sr);
			Document dt = db.parse(is);

			element = dt.getDocumentElement();
			System.out.println("根元素：" + element.getNodeName());
			String rootName = element.getNodeName();
			if (!rootName.equalsIgnoreCase("Notify")) {
				return null;
			}

			NodeList childNodes = element.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++)
			{
				Node node1 = childNodes.item(i);
				if ("CmdType".equalsIgnoreCase(node1.getNodeName())) {
					System.out.println("CmdType = " + node1.getTextContent());
					String content = node1.getTextContent();
					if (!content.equalsIgnoreCase("catalog")) {
						return null;
					}
				} else if ("SN".equalsIgnoreCase(node1.getNodeName())) {
					System.out.println("SN = " + node1.getTextContent());
				} else if ("DeviceID".equalsIgnoreCase(node1.getNodeName())) {
					System.out.println("Device ID = " + node1.getTextContent());
				} else if ("SumNum".equalsIgnoreCase(node1.getNodeName())) {
					System.out.println("SumNum = " + node1.getTextContent());
				} else if ("DeviceList".equalsIgnoreCase(node1.getNodeName())) {
					System.out.println("Device Count = " + node1.getAttributes().getNamedItem("Num").getNodeValue());
					NodeList nodeDetail = node1.getChildNodes();
					System.out.println("Item count = " + nodeDetail.getLength());
					for (int j = 0; j < nodeDetail.getLength(); j++) {
						Node detail = nodeDetail.item(j);
						System.out.println(detail.getNodeName());
						if ("Item".equals(detail.getNodeName())) {
							NodeList nodeDetail2 = detail.getChildNodes();
							for (int k = 0; k < nodeDetail2.getLength(); k++) {
								Node detail2 = nodeDetail2.item(k);
								if ("Event".equals(detail2.getNodeName())) {
									System.out.println("Event: " + detail2.getTextContent());
								} else if ("Name".equals(detail2.getNodeName())) {
									System.out.println("Name: " + detail2.getTextContent());
								} else if ("CivilCode".equals(detail2.getNodeName())) {
									System.out.println("CivilCode: " + detail2.getTextContent());
								} else if ("ParentID".equals(detail2.getNodeName())) {
									System.out.println("Parent ID: " + detail2.getTextContent());
								} else if ("Longitude".equals(detail2.getNodeName())) {
									System.out.println("Longitude: " + detail2.getTextContent());
								} else if ("Latitude".equals(detail2.getNodeName())) {
									System.out.println("Latitude: " + detail2.getTextContent());
								} else if ("Status".equals(detail2.getNodeName())) {
									System.out.println("Status: " + detail2.getTextContent());
								} else if ("DeviceID".equals(detail2.getNodeName())) {
									System.out.println("Status: " + detail2.getTextContent());
								}
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cameras;
	}
}
