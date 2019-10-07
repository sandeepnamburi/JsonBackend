package com.jsoncomparator.gateway;

import java.util.List;

interface AmazonGateway {
    List<String> getDirectories() throws Exception;
}