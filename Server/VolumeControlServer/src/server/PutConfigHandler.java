package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.postgresql.geometric.PGcircle;

import message.PutConfigReply;
import message.PutConfigRequest;
import model.Config;

public class PutConfigHandler extends DBHandler {
	private static final String INSERT_STMT = 
			"INSERT INTO \"Config\" (\"configName\", \"zone\", \"volumeRingtone\", \"volumeNotification\", \"userId\") " +
			"VALUES (?, ?, ?, ?, ?);";
	
	private static final String UPDATE_STMT = 
			"UPDATE \"Config\" SET(\"configName\", \"zone\", \"volumeRingtone\", \"volumeNotification\") = (?, ?, ?, ?) " +
			"WHERE \"configId\" = ?;";
	
	public PutConfigReply handle(PutConfigRequest request) {
		PutConfigReply reply = new PutConfigReply(false);
		
		Connection conn = null;
		try {
			conn = getConnection();
			
			Config conf = request.getUpdatedConfig();
			
			// If null, it's new one, else it's an update
			if(conf.getConfigId() == null) {
				PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
				ps.setObject(1, conf.getConfigName());
				PGcircle zone = new PGcircle(conf.getCenterX(), conf.getCenterY(), conf.getRadius());
				ps.setObject(2, zone);
				ps.setInt(3, conf.getVolumeRingtone());
				ps.setInt(4, conf.getVolumeNotification());
				ps.setString(5, request.getUserId());
				ps.executeUpdate();
			} else {
				PreparedStatement ps = conn.prepareStatement(UPDATE_STMT);
				ps.setObject(1, conf.getConfigName());
				PGcircle zone = new PGcircle(conf.getCenterX(), conf.getCenterY(), conf.getRadius());
				ps.setObject(2, zone);
				ps.setInt(3, conf.getVolumeRingtone());
				ps.setInt(4, conf.getVolumeNotification());
				ps.setInt(5, conf.getConfigId()); // WHERE
				ps.executeUpdate();
			}
			
			reply.setSuccess(true);
			// Auto-commit will commit
		} catch (SQLException e) {
			// Auto-commit will rollback
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		// TODO Check for ConfigID. if it's null, it's a new one, and we have to insert. else, it's an update.
		
		// TODO Connect to DB
		// TODO insert/update
		// TODO if bd fails, return error
		
		return reply;
	}

}