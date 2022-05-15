package com.wf.hackathon.service.ai;

import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microsoft.cognitiveservices.speech.CancellationDetails;
import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.audio.AudioInputStream;
import com.microsoft.cognitiveservices.speech.audio.AudioStreamFormat;
import com.microsoft.cognitiveservices.speech.audio.PushAudioInputStream;

public class TextToSpeechService {
    private SpeechConfig speechConfig;
    private AudioConfig audioConfig;
    private SpeechSynthesizer synthesizer;
    public TextToSpeechService(){
        speechConfig=SpeechConfig.fromSubscription(Constants.SPEECH__SUBSCRIPTION__KEY, Constants.SPEECH__SERVICE__REGION);
    }
    public String getSpeech(String name, String country,String gender){
        String targetVoice=null;
        if(gender.equals(Constants.FEMALE) || gender==null){
            targetVoice = Constants.femaleVoiceMap.get(country);
            if(targetVoice==null)
            targetVoice=Constants.femaleVoiceMap.get("United States");
        }
        else{
            targetVoice = Constants.maleVoiceMap.get(country);
            if(targetVoice==null)
            targetVoice=Constants.maleVoiceMap.get("United States");
        } 
        
        //String sourceLang = Constants.langMap.get(country);
        String sourceLang=Constants.langMap.get("United States");
        speechConfig.setSpeechSynthesisLanguage(sourceLang);
        speechConfig.setSpeechSynthesisVoiceName(targetVoice);
        synthesizer = new SpeechSynthesizer(speechConfig, null);
        SpeechSynthesisResult result = synthesizer.SpeakText(name);
        return Base64.getEncoder().encodeToString(result.getAudioData());
    }
    public String getSpeech(String name, String country){
        return getSpeech(name, country, null);
    }
    public String getSpeech(String audio,String country, String gender,String speed)
    {
        PushAudioInputStream pushStream = AudioInputStream.createPushStream(AudioStreamFormat.getDefaultInputFormat());
        pushStream.write(Base64.getDecoder().decode(audio));
        audioConfig= AudioConfig.fromStreamInput(pushStream);
        pushStream.close();
        String sourceLang = Constants.langMap.get(country);
        if(sourceLang==null)
        sourceLang=Constants.langMap.get("United States");
        speechConfig.setSpeechRecognitionLanguage(sourceLang);
        try (SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig)) {
            Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
            try {
                SpeechRecognitionResult speechRecognitionResult = task.get();
                if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
                    System.out.println("RECOGNIZED: Text=" + speechRecognitionResult.getText());

                }
                else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
                    System.out.println("NOMATCH: Speech could not be recognized.");
                    return "{'Error':'NOMATCH: Speech could not be recognized.'}";
                }
                else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
                    CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
                    System.out.println("CANCELED: Reason=" + cancellation.getReason());
                    if (cancellation.getReason() == CancellationReason.Error) {
                        System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                        System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails()+"");
                        return "{'Error':'CANCELED: Reason="+cancellation.getReason()+"  "+cancellation.getErrorDetails()+"'}";
                    }
                }

            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
        
    }
    // public String clenseVoice(String voice){

    // }
    public static void main(String[] args) {
        System.out.println(new TextToSpeechService().getSpeech(Test.AUDIO,"India","Female","x"));
    }
}
