/*
 * Copyright (C) 2014~2016 dinstone<dinstone@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dinstone.uams.cache;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonSerializer {

    private ObjectMapper objectMapper;

    public JacksonSerializer() {
        objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping();

        // JSON configuration not to serialize null field
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // JSON configuration for compatibility
        // objectMapper.configure(SerializationFeature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // objectMapper.configure(DeserializationFeature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public <T> byte[] serialize(T data) throws Exception {
        return objectMapper.writeValueAsBytes(data);
    }

    public <T> T deserialize(byte[] bodyBytes, Class<T> clazz) throws Exception {
        return objectMapper.readValue(bodyBytes, clazz);
    }

}
