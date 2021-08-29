package com.animesh.roy.spacexmission

import com.apollographql.apollo.ApolloClient

val apolloClient = ApolloClient.builder()
    .serverUrl("https://api.spacex.land/graphql/")
    .build()
