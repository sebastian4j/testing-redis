package com.sebastian.testing;

import io.lettuce.core.RedisClient;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.embedded.RedisServer;

/**
 * pruebas con redis.
 *
 * https://github.com/lettuce-io/lettuce-core
 * https://github.com/kstyrc/embedded-redis
 * 
 * @author Sebastián Ávila A.
 */
public class TestingRedis {

  private static RedisServer redisServer;

  @BeforeAll
  public static void iniciar() {
    redisServer = RedisServer.builder().port(6000).build();
    redisServer.start();
  }

  @AfterAll
  public static void finalizar() {
    redisServer.stop();
  }

  @Test
  public void redisFuncionaLocalmenteEnPruebas() {
    var client = RedisClient.create("redis://localhost:6000");
    try (var con = client.connect()) {
      var sync = con.sync();
      sync.set("clave", "valor");
      assertThat(sync.get("clave")).isEqualTo("valor");
    }
  }
}
