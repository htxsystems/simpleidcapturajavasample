package htxsystems.simpleid.javasample;

import com.google.gson.annotations.SerializedName;

enum OperationType
{
	@SerializedName("0")
    REGISTER(0),
    @SerializedName("2")
    SEARCH(2),
    @SerializedName("3")
    CANCEL_SEARCH(3),
    @SerializedName("4")
    DELETE(4),
    @SerializedName("8")
    GET_PERSON_BY_ID(8),
    @SerializedName("9")
    FINGER_START_CAPTURE(9),
    @SerializedName("10")
    FINGER_STOP_CAPTURE(10),
    @SerializedName("12")
    SUBMIT_REGISTRATION(12),
    @SerializedName("13")
    CANCEL_REGISTRATION(13),
    @SerializedName("14")
    DELETE_FINGER(14),
    @SerializedName("15")
    SEARCH_FACE(15),
    @SerializedName("16")
    INIT_FACE_ANALYSIS(16),
    @SerializedName("17")
    PERFORM_FACE_ANALYSIS(17),
    @SerializedName("18")
    STOP_FACE_ANALYSIS(18),
    @SerializedName("19")
    GET_CLIENT_VERSION(19);
    
	private final int value;
	OperationType(int value) { this.value = value; }
    public int getValue() { return value; }
    
    public static OperationType fromValue(int value) {
        for(OperationType type : OperationType.values()) {
             if(type.getValue() == value) {
                  return type;
             }
        }
        return null;
   }
}

enum FINGERID
{
	@SerializedName("0")
    UNKNOWN(0),
    @SerializedName("1")
    RIGHT_THUMB(1),
    @SerializedName("2")
    RIGHT_INDEX(2),
    @SerializedName("3")
    RIGHT_MIDDLE(3),
    @SerializedName("4")
    RIGHT_RING(4),
    @SerializedName("5")
    RIGHT_LITTLE(5),
    @SerializedName("6")
    LEFT_THUMB(6),
    @SerializedName("7")
    LEFT_INDEX(7),
    @SerializedName("8")
    LEFT_MIDDLE(8),
    @SerializedName("9")
    LEFT_RING(9),
    @SerializedName("10")
    LEFT_LITTLE(10);
    
	private final int value;
	FINGERID(int value) { this.value = value; }
    public int getValue() { return value; }
    
    public static FINGERID fromValue(int value) {
        for(FINGERID type : FINGERID.values()) {
             if(type.getValue() == value) {
                  return type;
             }
        }
        return null;
   }
}

class SimpleIDRequest
{
   
    private String APIKey;
    @SerializedName("Type")
	private OperationType OperationType;
	private String AccountID;
	private String PersonID;
	private String CustomField1;
	private String CustomField2;
	private String CustomField3;
	private FINGERID FingerID;
	private String FaceImage;
	private Boolean FaceOnly;
	private Boolean IgnoreSearch;



	public String getAPIKey() {
		return APIKey;
	}

	public void setAPIKey(String aPIKey) {
		APIKey = aPIKey;
	}

	public OperationType getOperationType() {
		return OperationType;
	}

	public void setOperationType(OperationType operationType) {
		OperationType = operationType;
	}

	public String getAccountID() {
		return AccountID;
	}

	public void setAccountID(String accountID) {
		AccountID = accountID;
	}

	public String getPersonID() {
		return PersonID;
	}

	public void setPersonID(String personID) {
		PersonID = personID;
	}

	public String getCustomField1() {
		return CustomField1;
	}

	public void setCustomField1(String customField1) {
		CustomField1 = customField1;
	}

	public String getCustomField2() {
		return CustomField2;
	}

	public void setCustomField2(String customField2) {
		CustomField2 = customField2;
	}

	public String getCustomField3() {
		return CustomField3;
	}

	public void setCustomField3(String customField3) {
		CustomField3 = customField3;
	}

	public FINGERID getFingerID() {
		return FingerID;
	}

	public void setFingerID(FINGERID fingerID) {
		FingerID = fingerID;
	}

	public String getFaceImage() {
		return FaceImage;
	}

	public void setFaceImage(String faceImage) {
		FaceImage = faceImage;
	}

	public Boolean getFaceOnly() {
		return FaceOnly;
	}

	public void setFaceOnly(Boolean faceOnly) {
		FaceOnly = faceOnly;
	}

	public Boolean getIgnoreSearch() {
		return IgnoreSearch;
	}

	public void setIgnoreSearch(Boolean ignoreSearch) {
		IgnoreSearch = ignoreSearch;
	}

	

    public SimpleIDRequest(String apiKey, OperationType operation)
    {
        APIKey = apiKey;
        OperationType = operation;
    }
}
