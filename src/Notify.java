import java.util.ArrayList;
import java.util.List;

public class Notify {
	private String cmdType = null;
	private String serialNum = null;
	private String deviceID = null;
	private int sumNum = 0;
	private List<Item> deviceList = new ArrayList<Item>();

	public String getCmdType() {
		return cmdType;
	}

	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public int getSumNum() {
		return sumNum;
	}

	public void setSumNum(int sumNum) {
		this.sumNum = sumNum;
	}

	public List<Item> getDeviceList() {
		return deviceList;
	}

	public void AddToDeviceList(Item item) {
		if (item != null) {
			this.deviceList.add(item);
		}
	}
	
	private void appendField(StringBuilder sb, String fieldName, String fieldValue) {
		sb.append("\t" + fieldName +" : ");
		sb.append(fieldValue);
		sb.append("\r\n");
	}
	
	private void appendDeviceInfo(StringBuilder sb, String fieldName, String fieldValue) {
		sb.append("\t\t\t" + fieldName +" : ");
		sb.append(fieldValue);
		sb.append("\r\n");
	}
	
	private void appendDevice(StringBuilder sb, Item item) {
		this.appendDeviceInfo(sb, "DeviceID", item.getDeviceID());
		this.appendDeviceInfo(sb, "Event", item.getEvent());
		
		if(item.getEvent().equalsIgnoreCase("ADD") || item.getEvent().equalsIgnoreCase("UPDATE")) {
			this.appendDeviceInfo(sb, "Name", item.getDeviceInfo().getName());
			this.appendDeviceInfo(sb, "CivilCode", item.getDeviceInfo().getCivilCode());
			this.appendDeviceInfo(sb, "ParentID", item.getDeviceInfo().getParentID());
			this.appendDeviceInfo(sb, "Longitude", String.valueOf(item.getDeviceInfo().getLongitude()));
			this.appendDeviceInfo(sb, "Latitude", String.valueOf(item.getDeviceInfo().getLatitude()));
			this.appendDeviceInfo(sb, "Status", String.valueOf(item.getDeviceInfo().getStatus()));
		}
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
		for(int i = 0; i < this.getDeviceList().size(); ++i) {
			sb.append("\t\tItem[" + i + "] : {\r\n");
			this.appendDevice(sb, this.getDeviceList().get(i));
			sb.append("\t\t}\r\n");
		}
		sb.append("\t}\r\n");
		
		
		sb.append("}");
		
		return sb.toString();
		
	}
}
