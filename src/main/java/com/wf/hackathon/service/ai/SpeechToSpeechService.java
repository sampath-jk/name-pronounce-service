package com.wf.hackathon.service.ai;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.audio.AudioInputStream;
import com.microsoft.cognitiveservices.speech.audio.AudioStreamFormat;
import com.microsoft.cognitiveservices.speech.audio.PushAudioInputStream;
import com.microsoft.cognitiveservices.speech.translation.*;

public class SpeechToSpeechService {

    private SpeechTranslationConfig speechConfig;
    private AudioConfig audioConfig;

    public SpeechToSpeechService() {
        speechConfig = SpeechTranslationConfig.fromSubscription(Constants.SPEECH__SUBSCRIPTION__KEY,
                Constants.SPEECH__SERVICE__REGION);
        speechConfig.setSpeechRecognitionLanguage(Constants.SPEECH_SOURCE_LANG);
        speechConfig.addTargetLanguage(Constants.SPEECH_TARGET_LANG);
    }

    public String getSpeech(String audio, String country, String gender, String speed) {
        //PushAudioInputStream pushStream = AudioInputStream.createPushStream(AudioStreamFormat.getDefaultInputFormat());
        PushAudioInputStream pushStream = AudioInputStream.createPushStream(AudioStreamFormat.getWaveFormatPCM((long)48000,(short)16,(short)2
        ));
        pushStream.write(Base64.getDecoder().decode(audio));
        audioConfig = AudioConfig.fromStreamInput(pushStream);
        pushStream.close();
        try (TranslationRecognizer speechRecognizer = new TranslationRecognizer(speechConfig, audioConfig)) {
            Future<TranslationRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
            TranslationRecognitionResult speechRecognitionResult = null;
            try {
                speechRecognitionResult = task.get();
            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (speechRecognitionResult.getReason() == ResultReason.TranslatedSpeech) {
                System.out.println("RECOGNIZED: Text=" + speechRecognitionResult.getText());
                return (new TextToSpeechService().getSpeech(speechRecognitionResult.getText(), country, gender,speed));
            } else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
                System.out.println("NOMATCH: Speech could not be recognized.");
                return "{'Error':'NOMATCH: Speech could not be recognized.'}";
            } else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
                CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());
                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails() + "");
                    return "{'Error':'CANCELED: Reason=" + cancellation.getReason() + "  "
                            + cancellation.getErrorDetails()
                            + "'}";
                }
            }
        }
        return "{'Error':'NOMATCH: Speech could not be recognized.'}";

    }

    public static void main(String[] args) {
        SpeechToSpeechService service = new SpeechToSpeechService();
       // System.out.println(service.getSpeech(Test.AUDIO, "India", "Female","slow"));
    }

}