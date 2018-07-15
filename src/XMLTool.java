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

public class XMLTool {
	public static void main(String[] args) {
		Notify notify = XMLTool.parseFromFile("data.xml");
		if (notify == null) {
			return;
		}
		System.out.println(notify);
	}

	public static Notify parseFromFile(String xmlFile) {
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

			return XMLTool.parseFromString(sb.toString());

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

	public static Notify parseFromString(String xmlContent) {
		Notify notify = null;
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
			String rootName = element.getNodeName();
			if (!rootName.equalsIgnoreCase("Notify")) {
				if (sr != null) {
					sr.close();
				}
				return null;
			}

			NodeList childNodes = element.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node1 = childNodes.item(i);
				switch (node1.getNodeName()) {
				case "CmdType":
					String content = node1.getTextContent();
					if (!content.equalsIgnoreCase("catalog")) {
						return null;
					}
					if (notify == null) {
						notify = new Notify();
					}
					notify.setCmdType(node1.getTextContent());
					break;
				case "SN":
					if (notify == null) {
						notify = new Notify();
					}
					notify.setSerialNum(node1.getTextContent());
					break;
				case "DeviceID":
					if (notify == null) {
						notify = new Notify();
					}
					notify.setDeviceID(node1.getTextContent());
					break;
				case "SumNum":
					if (notify == null) {
						notify = new Notify();
					}
					// TODO check: sumNum is number?
					notify.setSumNum(Integer.parseInt(node1.getTextContent()));
					break;
				case "DeviceList":
					NodeList nodeDetail = node1.getChildNodes();
					for (int j = 0; j < nodeDetail.getLength(); j++) {
						Node detail = nodeDetail.item(j);
						if ("Item".equals(detail.getNodeName())) {
							Item item = new Item();
							Device deviceInfo = null;
							NodeList nodeDetail2 = detail.getChildNodes();
							for (int k = 0; k < nodeDetail2.getLength(); k++) {
								Node detail2 = nodeDetail2.item(k);
								switch (detail2.getNodeName()) {
								case "DeviceID":
									item.setDeviceID(detail2.getTextContent());
									break;
								case "Event":
									item.setEvent(detail2.getTextContent());
									break;
								case "Name":
									// TODO design pattern for the similar code below:
									if (deviceInfo == null) {
										deviceInfo = new Device();
									}
									deviceInfo.setName(detail2.getTextContent());
									break;
								case "CivilCode":
									if (deviceInfo == null) {
										deviceInfo = new Device();
									}
									deviceInfo.setCivilCode(detail2.getTextContent());
									break;
								case "ParentID":
									if (deviceInfo == null) {
										deviceInfo = new Device();
									}
									deviceInfo.setParentID(detail2.getTextContent());
									break;
								case "Longitude":
									if (deviceInfo == null) {
										deviceInfo = new Device();
									}
									// TODO number check
									deviceInfo.setLongitude(Double.parseDouble(detail2.getTextContent()));
									break;
								case "Latitude":
									if (deviceInfo == null) {
										deviceInfo = new Device();
									}
									// TODO number check
									deviceInfo.setLatitude(Double.parseDouble(detail2.getTextContent()));
									break;
								case "Status":
									if (deviceInfo == null) {
										deviceInfo = new Device();
									}
									if ("ON".equalsIgnoreCase(detail2.getTextContent())) {
										deviceInfo.setStatus(DeviceStatus.ON);
									} else if ("OFF".equalsIgnoreCase(detail2.getTextContent())) {
										deviceInfo.setStatus(DeviceStatus.OFF);
									}
									break;
								}
							}

							if (deviceInfo != null) {
								item.setDeviceInfo(deviceInfo);
							}

							if (notify != null) {
								notify.AddToDeviceList(item);
							}
						}

					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notify;
	}
}
