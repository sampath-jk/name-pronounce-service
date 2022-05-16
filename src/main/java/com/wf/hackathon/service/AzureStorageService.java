package com.wf.hackathon.service;

import com.azure.spring.cloud.core.resource.AzureStorageBlobProtocolResolver;
import com.wf.hackathon.exception.NamePronounceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.OutputStream;
import java.nio.charset.Charset;

@Service
public class AzureStorageService {

    private final String containerName;
    private final ResourceLoader resourceLoader;
    private final AzureStorageBlobProtocolResolver azureStorageBlobProtocolResolver;

    public AzureStorageService(@Value("${spring.cloud.azure.storage.blob.container-name}") String containerName, ResourceLoader resourceLoader, AzureStorageBlobProtocolResolver azureStorageBlobProtocolResolver) {
        this.containerName = containerName;
        this.resourceLoader = resourceLoader;
        this.azureStorageBlobProtocolResolver = azureStorageBlobProtocolResolver;
    }

    public String uploadAudio(String audio, String fileName)  {
        Resource storageBlobResource = resourceLoader.getResource("azure-blob://" + containerName + "/" + fileName);
        try (OutputStream os = ((WritableResource) storageBlobResource).getOutputStream()) {
            os.write(audio.getBytes());
        } catch (Exception e) {
            throw new NamePronounceException("Exception while uploading audio file to azure");
        }
        return "uploaded";
    }

    public String readAudioFile(String fileName) {
        Resource storageBlobResource = resourceLoader.getResource("azure-blob://" + containerName + "/" + fileName);
        try {
            return StreamUtils.copyToString(
                    storageBlobResource.getInputStream(),
                    Charset.defaultCharset());
        } catch (Exception e) {
            throw new NamePronounceException("Exception while getting audion file from azure");
        }
    }
}

