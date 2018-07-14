import java.io.Serializable;

public class Camera implements Serializable {
	private String deviceID = null;
	private String name = null;
	private String civilCode = null;
	private String parentID = null;
	private double longitude = 0.0;
	private double latitude = 0.0;
	private CameraStatus status = null;

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

	public CameraStatus getStatus() {
		return status;
	}

	public void setStatus(CameraStatus status) {
		this.status = status;
	}

}