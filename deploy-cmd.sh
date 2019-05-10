-- 发布注意事项：
-- treasure-base作为公司顶级pom，变更很少，版本号单独定义；需要变更可以手动修改
-- 在treasure-parent-bom中统一定义了revision版本，可以直接使用mvn deploy来发布；需要发布新版本可以在pom中改变revision值即可；
-- 当然一个版本有正式版本xxx和快照版本xxx-SNAPSHOT，那么建议在pom中使用xxx-SHANSHOT，正式发布的时候使用mvn deploy -Drevision=xxx来即可
-- 发布正式版本时候mvn deploy -Drevision，命令中revision参数请参考treasure-parent-bom版本中的revision值

-- 发布treasure-base
cd treasure-base
mvn clean deploy -P sonatype-oss-release -N


-- 发布treasure-parent
mvn clean deploy -P release -Drevision=xxx-Release