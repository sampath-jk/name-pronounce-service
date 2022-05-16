package com.wf.hackathon.service;

import com.azure.spring.cloud.core.resource.AzureStorageBlobProtocolResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

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

    public String writeBlobFile(String data, String fileName)  {
        Resource storageBlobResource = resourceLoader.getResource("azure-blob://" + containerName + "/" + fileName);
        try (OutputStream os = ((WritableResource) storageBlobResource).getOutputStream()) {
            os.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "blob was updated";
    }
}

