package com.access.accessonandroid.Data;

import android.content.Context;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

/**
 * @author Tyler Collison
 *
 * This class provides a singleton instance of the wrapped Cognito Credentials used to connect to AWS
 * services directly.
 */
public class AWSCognitoCredentialProvider implements ICredentialProvider {

    //The Cognito Credentials ID
    private static final String IDENTITY_POOL_ID = "us-east-2:180a446f-81c6-4ed4-aea2-dfc6a3bcd2fe";

    //The credentials provider singleton
    private static ICredentialProvider instance;
    public static ICredentialProvider getInstance() {
        if (instance == null) {
            instance = new AWSCognitoCredentialProvider();
        }
        return instance;
    }

    //The Cognito Credentials
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
