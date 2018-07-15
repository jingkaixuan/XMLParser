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

public class Notify {
	public static final String ROOT_NODE_NAME = "Notify";
	public static final String DEVICE_NODE_NAME = "Item";
	public static final String DEVICE_LIST = "DeviceList";
	public static final String[] notifyFields = new String[] { "CmdType", "SN", "DeviceID", "SumNum", DEVICE_LIST };
	public static final String[] itemFields = new String[] { "DeviceID", "Event", "Name", "CivilCode", "ParentID",
			"Longitude", "Latitude", "Status" };

	public static Notify parseFromString(String xmlContent) {
		Notify notify = null;
		Element element = null;
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = null;
		StringReader sr = null;

		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();

			sr = new StringReader(xmlContent);
			InputSource is = new InputSource(sr);
			Document dt = db.parse(is);

			element = dt.getDocumentElement();
			String rootName = element.getNodeName();
			if (!rootName.equalsIgnoreCase(ROOT_NODE_NAME)) {
				return null;
			}

			notify = new Notify();
			NodeList childNodes = element.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node childNode = childNodes.item(i);
				String nodeName = childNode.getNodeName();
				for (String fieldName : Notify.notifyFields) {
					if (fieldName.equalsIgnoreCase(nodeName)) {
						if (!nodeName.equalsIgnoreCase(DEVICE_LIST)) {
							String methodName = "set" + fieldName;
							notify.getClass().getMethod(methodName, String.class).invoke(notify,
									childNode.getTextContent());
						} else {
							NodeList itemNodes = childNode.getChildNodes();
							for (int j = 0; j < itemNodes.getLength(); j++) {
								Node detail = itemNodes.item(j);
								if (DEVICE_NODE_NAME.equals(detail.getNodeName())) {
									Item item = new Item();
									NodeList nodeDetail2 = detail.getChildNodes();
									for (int k = 0; k < nodeDetail2.getLength(); k++) {
										Node detail2 = nodeDetail2.item(k);
										String nodeName2 = detail2.getNodeName();
										for (String field : Notify.itemFields) {
											if (field.equalsIgnoreCase(nodeName2)) {
												String methodName = "set" + field;
												item.getClass().getMethod(methodName, String.class).invoke(item,
														detail2.getTextContent());
												break;
											}
										}
									}
									notify.AddToDeviceList(item);
								}
							}
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sr != null) {
				sr.close();
			}
		}

		return notify;

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

			return Notify.parseFromString(sb.toString());

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

	private String cmdType = null;
	private String serialNum = null;
	private String deviceID = null;

	private String sumNum = null;

	private List<Item> deviceList = new ArrayList<Item>();

	public void AddToDeviceList(Item item) {
		if (item != null) {
			this.deviceList.add(item);
		}
	}

	private void appendDevice(StringBuilder sb, Item item) {
		this.appendDeviceInfo(sb, "DeviceID", item.getDeviceID());
		this.appendDeviceInfo(sb, "Event", item.getEvent());

		if (item.getEvent().equalsIgnoreCase("ADD") || item.getEvent().equalsIgnoreCase("UPDATE")) {
			this.appendDeviceInfo(sb, "Name", item.getName());
			this.appendDeviceInfo(sb, "CivilCode", item.getCivilCode());
			this.appendDeviceInfo(sb, "ParentID", item.getParentID());
			this.appendDeviceInfo(sb, "Longitude", String.valueOf(item.getLongitude()));
			this.appendDeviceInfo(sb, "Latitude", String.valueOf(item.getLatitude()));
			this.appendDeviceInfo(sb, "Status", String.valueOf(item.getStatus()));
		}
	}

	private void appendDeviceInfo(StringBuilder sb, String fieldName, String fieldValue) {
		sb.append("\t\t\t" + fieldName + " : ");
		sb.append(fieldValue);
		sb.append("\r\n");
	}

	private void appendField(StringBuilder sb, String fieldName, String fieldValue) {
		sb.append("\t" + fieldName + " : ");
		sb.append(fieldValue);
		sb.append("\r\n");
	}

	public String getCmdType() {
		return cmdType;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public List<Item> getDeviceList() {
		return deviceList;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public String getSumNum() {
		return sumNum;
	}

	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public void setSN(String serialNum) {
		this.serialNum = serialNum;
	}

	public void setSumNum(String sumNum) {
		this.sumNum = sumNum;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Notify : {\r\n");
		this.appendField(sb, "CmdType", this.cmdType);
		this.appendField(sb, "SN", this.serialNum);
		this.appendField(sb, "DeviceID", this.deviceID);
		this.appendField(sb, "SumNum", String.valueOf(this.sumNum));

		sb.append("\tDeviceList : {\r\n");
		for (int i = 0; i < this.getDeviceList().size(); ++i) {
			sb.append("\t\tItem[" + i + "] : {\r\n");
			this.appendDevice(sb, this.getDeviceList().get(i));
			sb.append("\t\t}\r\n");
		}
		sb.append("\t}\r\n");

		sb.append("}");

		return sb.toString();

	}
}
