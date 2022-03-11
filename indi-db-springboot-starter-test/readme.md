# TranscationTemplate
https://www.imooc.com/article/300692

TransactionTemplate 是事务处理的默认实现，可以参考写自己的回滚事务，实现TransactionOperations接口
TransactionCallback 接口是默认执行sql 的接口，实现TransactionCallback接口可以执行doInTransaction进行 TransactionStatus sql调用
TransactionStatus 是存在对sql执行过程中的hasSavepoint， 可以进行PlatformTransactionManager.rollBack(status)


# TransmittableThreadlocal
https://www.cnblogs.com/hama1993/p/10409740.html
