module com.marketterminal.data {
  requires transitive java.sql;
  requires org.slf4j;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.dataformat.yaml;
  requires java.net.http;
  requires com.clickhouse.jdbc;

  exports com.marketterminal.data.model;
  exports com.marketterminal.data.repository;
  exports com.marketterminal.data.service;
  exports com.marketterminal.data.loader;
  exports com.marketterminal.data.config;
}
