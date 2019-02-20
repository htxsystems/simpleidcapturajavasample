package htxsystems.simpleid.javasample;

import java.util.List;

import com.google.gson.annotations.SerializedName;

enum ProcessingStatus
{
	@SerializedName("0")
    LOCAL_PROCESSING(0),
    @SerializedName("1")
    FINISHED_LOCAL_PROCESSING(1),
    @SerializedName("2")
    SERVER_PROCESSING(2),
    @SerializedName("3")
    FINISHED_SERVER_PROCESSING(3),
    @SerializedName("4")
    REGISTRATION_READY(4),
    @SerializedName("5")
    PENDING_FINGER_UP(5),
    @SerializedName("6")
    PREPARING_SCANNER(6),
    @SerializedName("7")
    SCANNER_READY(7),
    @SerializedName("8")
    FINGER_DOWN(8),
    @SerializedName("9")
    FINGER_UP(9),
    @SerializedName("10")
    SCANNER_DISCONNECTED(10),
    @SerializedName("11")
    SCANNER_CONNECTED(11);
    
	private final int value;
	ProcessingStatus(int value) { this.value = value; }
    public int getValue() { return value; }
    
    public static ProcessingStatus fromValue(int value) {
        for(ProcessingStatus type : ProcessingStatus.values()) {
             if(type.getValue() == value) {
                  return type;
             }
        }
        return null;
   }
}

enum ResponseType
{
	@SerializedName("0")
    FINGER_SCANNER_STATUS(0),
    @SerializedName("1")
    FINGER_CAPTURE_STATUS(1),
    @SerializedName("2")
    REGISTRATION_PROCESS_RESPONSE(2),
    @SerializedName("3")
    SEARCH_PROCESS_RESPONSE(3),
    @SerializedName("4")
    CANCEL_SEARCH_RESPONSE(4),
    @SerializedName("5")
    SOCKET_GENERAL_MESSAGE(5),
    @SerializedName("6")
    DELETE_RESPONSE(6),
    @SerializedName("7")
    GET_PERSONS_BY_ID_RESPONSE(7),
    @SerializedName("8")
    PROCESSING_STATUS(8),
    @SerializedName("9")
    DUPLICATED_FINGER(9),
    @SerializedName("10")
    GENERAL_MESSAGE(10),
    @SerializedName("11")
    SEARCH_FACE_RESPONSE(11),
    @SerializedName("12")
    INIT_FACE_ANALYSIS_RESPONSE(12),
    @SerializedName("13")
    PERFORM_FACE_ANALYSIS_RESPONSE(13),
    @SerializedName("14")
    STOP_FACE_ANALYSIS_RESPONSE(14),
    @SerializedName("15")
    GET_CLIENT_VERSION_RESPONSE(15);
    
	private final int value;
	ResponseType(int value) { this.value = value; }
    public int getValue() { return value; }
    
    public static ResponseType fromValue(int value) {
        for(ResponseType type : ResponseType.values()) {
             if(type.getValue() == value) {
                  return type;
             }
        }
        return null;
   }
}

enum ReturnCode
{
	@SerializedName("0")
    SUCCESS (0),
    @SerializedName("1")
    CANCELED_BY_USER (1),
    @SerializedName("2")
    HIT_CONFIRMED (2),
    @SerializedName("3")
    NO_HIT (3),
    @SerializedName("4")
    ALREADY_ENROLLED (4),
    @SerializedName("5")
    CAPTURE_TIMEOUT (5),
    @SerializedName("6")
    BAD_IMAGE_QUALITY (6),
    @SerializedName("7")
    SERVICE_BUSY (7),
    @SerializedName("8")
    INCORRECT_CALL (8),
    @SerializedName("-1")
    NO_READER_CONNECTED (-1),
    @SerializedName("-2")
    SERVER_CONNECTION_FAILED  (-2),
    @SerializedName("-3")
    SERVER_TIMEOUT (-3),
    @SerializedName("-4")
    SERVER_ERROR (-4),
    @SerializedName("-5")
    PERSON_ID_NOT_FOUND (-5),
    @SerializedName("-6")
    INVALID_LICENSE (-6),
    @SerializedName("-7")
    LICENSE_MAX_RECORDS (-7),
    @SerializedName("-8")
    LICENSE_MAX_CLIENTS (-8),
    @SerializedName("-9")
    LICENSE_EXPIRED (-9),
    @SerializedName("-10")
    INVALID_APIKEY (-10),
    @SerializedName("-11")
    SESSION_EXPIRED (-11),
    @SerializedName("-12")
    NOT_ALLOWED_OPERATION (-12),
    @SerializedName("-13")
    INVALID_CREDENTIALS (-13),
    @SerializedName("-14")
    INVALID_PARAMETERS (-14),
    @SerializedName("-17")
    PARSER_ERROR (-17),
    @SerializedName("-18")
    FACE_MODULE_OPERATION_FAILED (-18),
    @SerializedName("-19")
    FACE_MODULE_NOT_INITIALIZED (-19),
    @SerializedName("-20")
    ACCOUNT_NOT_FOUND(-20),
    @SerializedName("-21")
    SERVER_NOT_READY(-21);
    
	private final int value;
	ReturnCode(int value) { this.value = value; }
    public int getValue() { return value; }
    
    public static ReturnCode fromValue(int value) {
        for(ReturnCode type : ReturnCode.values()) {
             if(type.getValue() == value) {
                  return type;
             }
        }
        return null;
   }
}


enum BrightnessLevel
{
	@SerializedName("-1")
    TOO_DARK (-1),
    @SerializedName("0")
    OK  (0),
    @SerializedName("1")
    TOO_LIGHT  (1);
    
	private final int value;
	BrightnessLevel(int value) { this.value = value; }
    public int getValue() { return value; }
    
    public static BrightnessLevel fromValue(int value) {
        for(BrightnessLevel type : BrightnessLevel.values()) {
             if(type.getValue() == value) {
                  return type;
             }
        }
        return null;
   }
}

class Eye
{
    private int X;
	private int Y;
	private Boolean Open;
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	
    public Boolean getOpen() {
		return Open;
	}
	public void setOpen(Boolean open) {
		Open = open;
	}
}

class Rectangle
{
  
    private int X;
	private int Y;
	private int Width;
	private int Height;

	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public int getWidth() {
		return Width;
	}
	public void setWidth(int width) {
		Width = width;
	}
	public int getHeight() {
		return Height;
	}
	public void setHeight(int height) {
		Height = height;
	}
	
    public Rectangle(int x, int y, int height, int width)
    {
        X = x;
        Y = y;
        Width = width;
        Height = height;
    }
}

class FaceImageAnalysis
{
  
    private Rectangle Rectangle;
	private htxsystems.simpleid.javasample.Rectangle CropRectangle;
	private Eye RightEye;
	private Eye LeftEye;
	private int Yaw;
	private int Pitch;
	private int Roll;
	private BrightnessLevel Brightness;
	private Boolean Sharpness;
	private Boolean MouthClosed;
	public Rectangle getRectangle() {
		return Rectangle;
	}
	public void setRectangle(Rectangle rectangle) {
		Rectangle = rectangle;
	}
	public Rectangle getCropRectangle() {
		return CropRectangle;
	}
	public void setCropRectangle(Rectangle cropRectangle) {
		CropRectangle = cropRectangle;
	}
	public Eye getRightEye() {
		return RightEye;
	}
	public void setRightEye(Eye rightEye) {
		RightEye = rightEye;
	}
	public Eye getLeftEye() {
		return LeftEye;
	}
	public void setLeftEye(Eye leftEye) {
		LeftEye = leftEye;
	}
	public int getYaw() {
		return Yaw;
	}
	public void setYaw(int yaw) {
		Yaw = yaw;
	}
	public int getPitch() {
		return Pitch;
	}
	public void setPitch(int pitch) {
		Pitch = pitch;
	}
	public int getRoll() {
		return Roll;
	}
	public void setRoll(int roll) {
		Roll = roll;
	}
	public BrightnessLevel getBrightness() {
		return Brightness;
	}
	public void setBrightness(BrightnessLevel brightness) {
		Brightness = brightness;
	}
	public Boolean getSharpness() {
		return Sharpness;
	}
	public void setSharpness(Boolean sharpness) {
		Sharpness = sharpness;
	}
	public Boolean getMouthClosed() {
		return MouthClosed;
	}
	public void setMouthClosed(Boolean mouthClosed) {
		MouthClosed = mouthClosed;
	}

}

class CapturedFingerprintImage
{
   
    private int Quality;
	private String Image;
	private FINGERID FingerID;
	private FINGERID DuplicatedFingerID;
	private Boolean Finished;
	public int getQuality() {
		return Quality;
	}
	public void setQuality(int quality) {
		Quality = quality;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public FINGERID getFingerID() {
		return FingerID;
	}
	public void setFingerID(FINGERID fingerID) {
		FingerID = fingerID;
	}
	public FINGERID getDuplicatedFingerID() {
		return DuplicatedFingerID;
	}
	public void setDuplicatedFingerID(FINGERID duplicatedFingerID) {
		DuplicatedFingerID = duplicatedFingerID;
	}
	public Boolean getFinished() {
		return Finished;
	}
	public void setFinished(Boolean finished) {
		Finished = finished;
	}

}

class PersonDTO
{
   
    private String PersonID;
	private String CustomField1;
	private String CustomField2;
	private String CustomField3;
	private CapturedFingerprintImage FingerprintImage;
	private FaceImageAnalysis FaceImageAnalysis;
	private String FaceImage;
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
	public CapturedFingerprintImage getFingerprintImage() {
		return FingerprintImage;
	}
	public void setFingerprintImage(CapturedFingerprintImage fingerprintImage) {
		FingerprintImage = fingerprintImage;
	}
	public FaceImageAnalysis getFaceImageAnalysis() {
		return FaceImageAnalysis;
	}
	public void setFaceImageAnalysis(FaceImageAnalysis faceImageAnalysis) {
		FaceImageAnalysis = faceImageAnalysis;
	}
	public String getFaceImage() {
		return FaceImage;
	}
	public void setFaceImage(String faceImage) {
		FaceImage = faceImage;
	}

}

class TransactionInformation
{

    private ResponseType Reason;
	private ReturnCode ReturnCode;
	private String Message;
	private String TransacationID;
	private String AccountID;
	private ProcessingStatus ProcessingStatus;
	private List<PersonDTO> Persons;
	public ResponseType getReason() {
		return Reason;
	}
	public void setReason(ResponseType reason) {
		Reason = reason;
	}
	public ReturnCode getReturnCode() {
		return ReturnCode;
	}
	public void setReturnCode(ReturnCode returnCode) {
		ReturnCode = returnCode;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getTransacationID() {
		return TransacationID;
	}
	public void setTransacationID(String transacationID) {
		TransacationID = transacationID;
	}
	public String getAccountID() {
		return AccountID;
	}
	public void setAccountID(String accountID) {
		AccountID = accountID;
	}
	public ProcessingStatus getProcessingStatus() {
		return ProcessingStatus;
	}
	public void setProcessingStatus(ProcessingStatus processingStatus) {
		ProcessingStatus = processingStatus;
	}
	public List<PersonDTO> getPersons() {
		return Persons;
	}
	public void setPersons(List<PersonDTO> persons) {
		Persons = persons;
	}
	
}

class SimpleIDResponse
{
   
	private TransactionInformation TransactionInformation;
	public TransactionInformation getTransactionInformation() {
		return TransactionInformation;
	}
	public void setTransactionInformation(TransactionInformation transactionInformation) {
		TransactionInformation = transactionInformation;
	}
}

