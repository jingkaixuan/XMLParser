import java.util.ArrayList;
import java.util.List;

public class Device {
	private String deviceID = null;
	private String name = null;
	private String civilCode = null;
	private String parentID = null;
	private double longitude = 0.0;
	private double latitude = 0.0;
	private DeviceStatus status = null;
	
	private List<Device> subDevices = new ArrayList<Device>();
	
	public void addToSubDevices(Device device) {
		if(device != null) {
			this.subDevices.add(device);
		}
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCivilCode() {
		return civilCode;
	}

	public void setCivilCode(String civilCode) {
		this.civilCode = civilCode;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public DeviceStatus getStatus() {
		return status;
	}

	public void setStatus(DeviceStatus status) {
		this.status = status;
	}

	public List<Device> getDeviceList() {
		return subDevices;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.subDevices = deviceList;
	}

}