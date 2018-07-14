import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Tester {
	public static void main(String[] args) {
		Camera ps = Tester.parseFromFile("data.xml");
	}

	public static Camera parseFromFile(String xmlFile) {
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
	public static Camera domXml(String xmlContent) {
		Camera camera = new Camera();
		Element element = null;
		DocumentBuilder db = null; // documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
		DocumentBuilderFactory dbf = null;
		try {
			dbf = DocumentBuilderFactory.newInstance(); // 返回documentBuilderFactory对象
			db = dbf.newDocumentBuilder();// 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象

			StringReader sr = new StringReader(xmlContent);
			InputSource is = new InputSource(sr);
			Document dt = db.parse(is); // 得到一个DOM并返回给document对象

			element = dt.getDocumentElement();// 得到一个elment根元素
			System.out.println("根元素：" + element.getNodeName()); // 获得根节点
			String rootName = element.getNodeName();
			if (!rootName.equalsIgnoreCase("Notify")) {
				return null;
			}

			NodeList childNodes = element.getChildNodes(); // 获得根元素下的子节点
			for (int i = 0; i < childNodes.getLength(); i++) // 遍历这些子节点
			{
				Node node1 = childNodes.item(i); // childNodes.item(i);
				// 获得每个对应位置i的结点
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
					NodeList nodeDetail = node1.getChildNodes(); // 获得<Accounts>下的节点
					System.out.println("Item count = " + nodeDetail.getLength());
					for (int j = 0; j < nodeDetail.getLength(); j++) { // 遍历<Accounts>下的节点
						Node detail = nodeDetail.item(j); // 获得<Accounts>元素每一个节点
						System.out.println(detail.getNodeName());
						if ("Item".equals(detail.getNodeName())) { // 输出PUB
							NodeList nodeDetail2 = detail.getChildNodes();
							for (int k = 0; k < nodeDetail2.getLength(); k++) {
								Node detail2 = nodeDetail2.item(k);
								if ("Event".equals(detail2.getNodeName())) { // 输出pass
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
		return camera;
	}
}
