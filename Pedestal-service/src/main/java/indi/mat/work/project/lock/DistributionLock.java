@Service
public class DistributionLock {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Connection lock() {
        Connection connection = null;
        try{
            connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstmt2 = connection.prepareStatement("SELECT * FROM t_lock WHERE method_name='appointment_number' FOR UPDATE");
            pstmt2.executeQuery();
            pstmt2.close();
            return connection;
        }catch (Exception e){
            throw new SystemException("create appointment fail",e);
        }

    }


    public void unlock(Connection connection){
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SystemException("create appointment fail",e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new SystemException("create appointment fail",e);
            }
        }
    }
}
