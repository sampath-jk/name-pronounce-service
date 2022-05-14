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


public class NamePronounceService {

    private SpeechTranslationConfig config;
    private AudioConfig audioConfig;
    private TranslationRecognizer recognizer;
     // Set audio format
    final long samplesPerSecond = 16000;
    final short bitsPerSample = 16;
    final short channels = 8;
    
    public NamePronounceService()
    {
        config=SpeechTranslationConfig.fromSubscription(Constants.SPEECH__SUBSCRIPTION__KEY, Constants.SPEECH__SERVICE__REGION);
        config.setSpeechRecognitionLanguage(Constants.SPEECH_SOURCE_LANG);
        config.addTargetLanguage(Constants.SPEECH_TARGET_LANG);
       // audioConfig= AudioConfig.fromDefaultMicrophoneInput();

       // audioConfig= AudioConfig.fromWavFileInput("D:\\Hackathon\\workspace\\name-pronounce-service\\src\\main\\java\\com\\wf\\hackathon\\service\\ai\\time.wav");
       // PushAudioInputStream pushStream = AudioInputStream.createPushStream(AudioStreamFormat.getWaveFormatPCM(samplesPerSecond, bitsPerSample, channels));
       PushAudioInputStream pushStream = AudioInputStream.createPushStream(AudioStreamFormat.getDefaultInputFormat());
         try {
            byte[] voiceSampleData = Files.readAllBytes(Paths.get("D:\\Hackathon\\workspace\\name-pronounce-service\\src\\main\\java\\com\\wf\\hackathon\\service\\ai\\time.wav"));
            // pushStream.write(new Test().readStream());
            pushStream.write(voiceSampleData);
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
        audioConfig= AudioConfig.fromStreamInput(pushStream);
       // pushStream.close();
        recognizer= new TranslationRecognizer(config,audioConfig);
        
    }
    public void testSpeechToText(){
        System.out.printf("Say something in  and we'll translate...");

        TranslationRecognitionResult result;
        try {
            result = recognizer.recognizeOnceAsync().get();
            if (result.getReason() == ResultReason.TranslatedSpeech) {
                System.out.printf("Recognized: \"%s\"\n", result.getText());
                for (Map.Entry<String, String> pair : result.getTranslations().entrySet()) {
                    System.out.printf("Translated into '%s': %s\n", pair.getKey(), pair.getValue());
                }
            }    
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        }
        public static void main(String[] args) {
            NamePronounceService service=new NamePronounceService();
            service.testSpeechToText();
        }

}