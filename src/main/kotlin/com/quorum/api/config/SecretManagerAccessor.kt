package com.quorum.api.config

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient
import com.google.cloud.secretmanager.v1.SecretVersionName

object SecretManagerAccessor {

    fun getSecret(secretId: String, versionId: String = "latest"): String {
        val projectId = "quorum-api"
        SecretManagerServiceClient.create().use { client ->
            val secretVersionName = SecretVersionName.of(projectId, secretId, versionId)
            val response: AccessSecretVersionResponse = client.accessSecretVersion(secretVersionName)
            return response.payload.data.toStringUtf8()
        }
    }
}
