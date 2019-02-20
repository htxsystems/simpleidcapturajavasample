package htxsystems.simpleid.javasample;

import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.Gson;

public class SimpleID {

	 private WebsocketClientEndpoint _ws;
     private String _wsURL = "ws://localhost:7001/operations/";
     private String _apiKey = "YOUR_API_KEY";
     private SimpleIDEvent _listener;
     
     
     public SimpleID(SimpleIDEvent listener) {
    	 _listener = listener;
     }
     
     public void Connect() {
    	 try {
			_ws = new WebsocketClientEndpoint(new URI(_wsURL));
			
			 _ws.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
	             public void handleMessage(String message) {
	            	 
	            	 Gson gson = new Gson();
	            	 SimpleIDResponse response = gson.fromJson(message, SimpleIDResponse.class);
	            	 FireEvent(response);
	            	 
	             }
	         });
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
     }
     
     public void Disconnect() {
    	 _ws.disconnect();
     }
     
   
     public void StartRegister(String accountId, String personId, String custom1, 
    		 String custom2, String custom3, String faceImage, Boolean faceOnly)
         {
             SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.REGISTER);
             request.setAccountID(accountId);
             request.setPersonID(personId);
             request.setCustomField1(custom1);
             request.setCustomField2(custom2);
             request.setCustomField3(custom3);
             request.setFaceImage(faceImage);
             request.setFaceOnly(faceOnly);

             Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void SubmitRegister()
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.SUBMIT_REGISTRATION);

        	 Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void CancelRegister()
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.CANCEL_REGISTRATION);

        	 Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void Delete(String accountId, String personId)
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.DELETE);
        	 request.setAccountID(accountId);
             request.setPersonID(personId);

             Gson gson = new Gson();
             String message = gson.toJson(request);
             _ws.sendMessage(message);
         }

         public void SearhFingerprint(String accountId, String personId)
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.SEARCH);
        	 request.setAccountID(accountId);
             request.setPersonID(personId);

             Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void CancelFingerprintSearch()
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.CANCEL_SEARCH);
        	 
        	 Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void SearchFace(String accountId, String personId, String faceImage)
         {

        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.SEARCH_FACE);
        	 request.setAccountID(accountId);
             request.setPersonID(personId);
             request.setFaceImage(faceImage);

             Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void GetPersonByID(String accountId, String personId, String custom1)
         {

        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.GET_PERSON_BY_ID);
        	 request.setAccountID(accountId);
             request.setPersonID(personId);
             request.setCustomField1(custom1);

             Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void StartFingerCapture(FINGERID fingerId)
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.FINGER_START_CAPTURE);
        	 request.setFingerID(fingerId);
             Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void StopFingerCapture()
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.FINGER_STOP_CAPTURE);
        	 Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void DeleteCapturedFinger()
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.DELETE_FINGER);
        	 Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void StartFaceAnalysis()
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.INIT_FACE_ANALYSIS);
        	 Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void PerformFaceAnalysis(String faceImage)
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.PERFORM_FACE_ANALYSIS);
        	 request.setFaceImage(faceImage);
             Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }

         public void StopFaceAnalysis()
         {
        	 SimpleIDRequest request = new SimpleIDRequest(_apiKey, OperationType.STOP_FACE_ANALYSIS);
        	 Gson gson = new Gson();
             
             _ws.sendMessage(gson.toJson(request));
         }
     
     
     private void FireEvent(SimpleIDResponse response)
     {
    	 if(_listener != null)
    	 {
    		 _listener.OnSimpleIDMessage(response);
    	 }
     }
	
}
