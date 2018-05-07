package powermacros.transforms;

import burp.*;
import powermacros.extract.Extraction;
import powermacros.replace.Replace;

import javax.script.*;
import javax.xml.crypto.dsig.Transform;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;

public class ExtractReplaceScript extends ExtractReplaceMethod {
    private String scriptLanguage;

    public ExtractReplaceScript(Extraction extraction, String scriptPath, TransformTypes type) {
        super(extraction);
        this.typeArgs = scriptPath;
        this.scriptLanguage = type.text();
    }

    public ExtractReplaceScript(Replace replace, String scriptPath, TransformTypes type) {
        super(replace);
        this.typeArgs = scriptPath;
        this.scriptLanguage = type.text();
    }

    @Override
    public String getReplacedExtraction(String requestResponse) {
        ScriptEngineManager factory = new ScriptEngineManager();
        // escape all the single quotes or do other required modifications to the body
        ScriptEngine engine = factory.getEngineByName(this.scriptLanguage);

        // set the RequestBody for the javascript functionality
        engine.put("requestBody",requestResponse);
        // execute the js

        try {
            engine.eval(new java.io.FileReader(this.typeArgs)); //, context);
        } catch (Exception e) {
            BurpExtender.println("Script read error.");
        }

        // read the result variable from the js
        Object res = engine.get("result");
        // and return it
        return res.toString();
    }

    public void PostProcessAction(IHttpRequestResponse currentRequest, IHttpRequestResponse[] macroItems) {
        // get the HTTP service for the request
        IHttpService httpService = currentRequest.getHttpService();

        // get the URL of the request
        URL url = BurpExtender.getInstance().helpers.analyzeRequest(currentRequest).getUrl();

        // if the target host is the right one and the url is not e.g login
        if (BurpExtender.HOST_FROM.equalsIgnoreCase(httpService.getHost())) {
            // get the request info
            IRequestInfo rqInfo = BurpExtender.getInstance().helpers.analyzeRequest(currentRequest);
            // retrieve all headers
            List<String> headers = rqInfo.getHeaders();
            // get the request
            String request = new String(currentRequest.getRequest());
            // get the request body
            String messageBody = request.substring(rqInfo.getBodyOffset());
            String signature = null;
            try {
                signature = PostProcessSignBody(messageBody);
            } catch (Exception e) {
                BurpExtender.getInstance().stdout.println(e.toString());
            }

            // go through the header and look for the one that we want to replace
            for (int i = 0; i < headers.size(); i++) {
                if (headers.get(i).startsWith("Signature-Header:"))
                    headers.set(i, "Signature-Header: " + signature);
            }

            // create the new http message with the modified header
            byte[] message = BurpExtender.getInstance().helpers.buildHttpMessage(headers, messageBody.getBytes());
            // print out the debug message if applicable (will be shown in the ui of Burp)
            // replace the current request and forward it
            currentRequest.setRequest(message);
        }
    }

    public String PostProcessSignBody(String requestbody) throws GeneralSecurityException, FileNotFoundException, IOException, ScriptException {
        ScriptEngineManager factory = new ScriptEngineManager();
        // escape all the single quotes or do other required modifications to the body
        String bodyRequest = requestbody.replace("'", "\'");

        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // set the RequestBody for the javascript functionality
        engine.put("requestBody", bodyRequest);
        // execute the js
        engine.eval(new java.io.FileReader("/path/to/sign.js"));
        // read the result variable from the js
        Object res = engine.get("result");
        // and return it
        return res.toString();
    }

}
