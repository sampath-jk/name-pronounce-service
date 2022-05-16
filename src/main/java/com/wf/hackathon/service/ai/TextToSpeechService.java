package com.wf.hackathon.service.ai;

import java.util.Base64;

import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;

public class TextToSpeechService {
    private SpeechConfig speechConfig;
    private SpeechSynthesizer synthesizer;

    public TextToSpeechService() {
        speechConfig = SpeechConfig.fromSubscription(Constants.SPEECH__SUBSCRIPTION__KEY,
                Constants.SPEECH__SERVICE__REGION);
    }

    public String getSpeech(String name, String country, String gender, String speed) {
        String targetVoice = null;
        if (gender.equals(Constants.FEMALE) || gender == null) {
            targetVoice = Constants.femaleVoiceMap.get(country);
            if (targetVoice == null)
                targetVoice = Constants.femaleVoiceMap.get("United States");
        } else {
            targetVoice = Constants.maleVoiceMap.get(country);
            if (targetVoice == null)
                targetVoice = Constants.maleVoiceMap.get("United States");
        }

        // String sourceLang = Constants.langMap.get(country);
        String sourceLang = Constants.langMap.get("United States");
        speechConfig.setSpeechSynthesisLanguage(sourceLang);
        speechConfig.setSpeechSynthesisVoiceName(targetVoice);
        synthesizer = new SpeechSynthesizer(speechConfig, null);
        SpeechSynthesisResult result;
        if (speed == null || (speed != null && speed.equals(Constants.SPEED_NORMAL)))
            result = synthesizer.SpeakText(name);
        else {
            String ssml = "<speak version=\"1.0\" xmlns=\"http://www.w3.org/2001/10/synthesis\" xml:lang=\"en-US\">\n" +
                    "<voice name=\""+targetVoice+"\">\n" +
                    "<prosody rate=\"" + speed + "\">\n" +
                    name +
                    "</prosody>\n" +
                    "</voice>\n" +
                    "</speak>";
            result = synthesizer.SpeakSsml(ssml);
        }
        return Base64.getEncoder().encodeToString(result.getAudioData());
    }
    public String getSpeech(String name, String country) {
        return getSpeech(name, country, null, null);
    }

    // public String clenseVoice(String voice){

    // }
    public static void main(String[] args) {
        System.out.println(new TextToSpeechService().getSpeech("Jagadeesh", "Pakistan", "Male", null));
    }
}
