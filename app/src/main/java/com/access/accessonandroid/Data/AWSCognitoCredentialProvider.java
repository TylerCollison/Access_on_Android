package com.access.accessonandroid.Data;

import android.content.Context;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

/**
 * Created by Tyler Collison on 11/10/2017.
 */

public class AWSCognitoCredentialProvider implements ICredentialProvider {

    private static final String IDENTITY_POOL_ID = "us-east-2:0f555d1a-d025-4f53-ba52-2e5a4b4d0f6f";

    private static ICredentialProvider instance;
    public static ICredentialProvider getInstance() {
        if (instance == null) {
            instance = new AWSCognitoCredentialProvider();
        }

        return instance;
    }

    private CognitoCachingCredentialsProvider credentialsProvider;

    @Override
    public void CreateProvider(Context context) {
        // Initialize the Amazon Cognito credentials provider
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context, IDENTITY_POOL_ID, Regions.US_EAST_2);
    }

    @Override
    public AWSCredentialsProvider GetProvider() {
        return credentialsProvider;
    }
}
