package be.andrei.aroadz.model;

public class KalmanFilter {

	private float X0 = 0; // predicted state
	private float P0 = 0; // predicted covariance

	private float Q = 2;  // default values
    private float R = 15; // environment noise

    private float K = 0;
	
    private float state = 0; 
    private float covariance = 1; 
    
    

    public KalmanFilter(float state, float covariance ){
        this.state = state;
        this.covariance = covariance;
    }
    
    public KalmanFilter(float state ) {
        this.state = state;

    }
    
    public KalmanFilter(){

    }

    public float correct(float data){
        //time update - prediction
        X0 = state;
        P0 = covariance + Q;

        //measurement update - correction
        K = P0/(P0 + R);
        state = X0 + K*(data - X0);
        covariance = (1 - K)*P0;
        
        return state;
    }
	
}
