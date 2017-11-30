package com.access.accessonandroid.Data;

import android.content.Context;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;

/**
 * @author Tyler Collison
 *
 * This defines the interface for all credentials providers.
 */
public interface ICredentialProvider {

    /**
     * Create the credentials
     * @param context The context for which to create the credentials
     */
    void CreateProvider(Context context);

    /**
     * Get the credentials
     * @return An AWS credentials provider
     */
    AWSCredentialsProvider GetProvider();

}
