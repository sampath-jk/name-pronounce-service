package com.wf.hackathon.controller;

import com.wf.hackathon.model.CustomPronounceRequest;
import com.wf.hackathon.model.PronounceRequest;
import com.wf.hackathon.service.NamePronounceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NamePronounceControllerTest {

    @InjectMocks
    private NamePronounceController namePronounceController;

    @Mock
    private NamePronounceService namePronounceService;

    @Test
    void pronounceName() {
        PronounceRequest request = new PronounceRequest();
        Mockito.when(namePronounceService.pronounceName(Mockito.any())).thenReturn(new HashMap<>());
        namePronounceController.pronounceName(request);
    }

    @Test
    void customPronounce() {
        Mockito.when(namePronounceService.customPronounceNameTest(Mockito.any())).thenReturn(new HashMap<>());
        namePronounceController.customPronounce(new CustomPronounceRequest());
    }

    @Test
    void resetPronunciation() {
        Mockito.when(namePronounceService.resetPronunciation(Mockito.anyString())).thenReturn(new HashMap<>());
        namePronounceController.resetPronunciation("DD");
    }

    @Test
    void savePronunciation() {
        Mockito.when(namePronounceService.customPronounceName(Mockito.any())).thenReturn(new HashMap<>());
        namePronounceController.savePronunciation(new CustomPronounceRequest());
    }
}