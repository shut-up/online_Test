package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 权限表
 */
@Document(collection="editor")
public class Editor extends  BaseEntity{
}
