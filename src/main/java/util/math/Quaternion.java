package util.math;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 * Representation of a Quaternion of the form q = xi + yj + zk + w, with
 * imaginary parts x, y, z and scalar part w.
 * 
 * @author Dustin Biser
 * 
 */
public class Quaternion {
	// Imaginary components.
	public float x = 0f;
	public float y = 0f;
	public float z = 0f;
	
	// Real component.
	public float w = 0f;
	
	private Matrix4f rotationMatrix = new Matrix4f();
	private boolean rotationMatrixNeedsUpdate = true;
	
	// Temporary vector to aid in rotation calculations.
	private static final Vector4f vRotTemp = new Vector4f();
	
	//--------------------------------------------------------------------------
	/**
	 * Default constructor.
	 */
	public Quaternion() {
		
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Copy constructor.
	 * @param q
	 */
	public Quaternion(Quaternion q) {
		init(q.x, q.y, q.z, q.w);
	}
	
	//--------------------------------------------------------------------------
	public Quaternion(float x, float y, float z, float w) {
		init(x, y, z, w);
	}
	
	
	//--------------------------------------------------------------------------
	/**
	 * Constructs a Quaternion representing a ccw rotation by <code>angle</code>
	 * radians about the <code>axis</code> given.
	 * 
	 * @param axis - axis of rotation.
	 * @param angle - angle of rotation in radians.
	 */
	public Quaternion(Vector3f axis, float angle) {
		// Set to unit Quaternion.
		fromAxisAngle(axis, angle);
	}
	
	//--------------------------------------------------------------------------
	private void init(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Sets this <code>Quaternion</code> so that it represents a ccw rotation
	 * about the <code>axis</code> given by <code>angle</code> radians.
	 * 
	 * A side effect is that this will be a unit Quaternion with norm() == 1.
	 * 
	 * @param axis - axis of rotation.
	 * @param angle - angle of rotation in radians.
	 */
	public void fromAxisAngle(Vector3f axis, float angle) {
		// Set this quaternion to (sin(angle/2)*u, cos(angle/2))  with u, a unit vector.
		
		w = (float) (1f / Math.sqrt((axis.x*axis.x + axis.y*axis.y + axis.z*axis.z)));
		
		x = axis.x * w;
		y = axis.y * w;
		z = axis.z * w;
		
		w =  (float) Math.sin(0.5f * angle);
		x *= w;
		y *= w;
		z *= w;
		
		w = (float) Math.cos(0.5f * angle);
		
		invalidateRotationMatrix();
	}
	
	//--------------------------------------------------------------------------
	@Override 
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		else if (!(other instanceof Quaternion)) {
			return false;
		}
		else if (this == other) {
			return true;
		}
	
		Quaternion o = (Quaternion) other;
		
		return MathUtils.floatEqualsUlp(x, o.x) &&
			   MathUtils.floatEqualsUlp(y, o.y) &&
			   MathUtils.floatEqualsUlp(z, o.z) &&
			   MathUtils.floatEqualsUlp(w, o.w);
	}
	
	//--------------------------------------------------------------------------
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Quaternion(");
		sb.append(x);
		sb.append(", ");
		sb.append(y);
		sb.append(", ");
		sb.append(z);
		sb.append(", ");
		sb.append(w);
		sb.append(")");
		
		return sb.toString();
	}
	
	//--------------------------------------------------------------------------
	public static void add(Quaternion lhs, Quaternion rhs, Quaternion dest) {
		dest.x = lhs.x + rhs.x;
		dest.y = lhs.y + rhs.y;
		dest.z = lhs.z + rhs.z;
		dest.w = lhs.w + rhs.w;
		
		dest.invalidateRotationMatrix();
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Add two Quaternions and return the result.
	 * 
	 * @param lhs
	 * @param rhs
	 * @return a new Quaternion which is the result of lhs added to rhs.
	 */
	public static Quaternion add(Quaternion lhs, Quaternion rhs) {
		Quaternion result = new Quaternion();
		add(lhs, rhs, result);
		
		return result;
	}
	
	//--------------------------------------------------------------------------
	public static void subtract(Quaternion lhs, Quaternion rhs, Quaternion dest) {
		dest.x = lhs.x - rhs.x;
		dest.y = lhs.y - rhs.y;
		dest.z = lhs.z - rhs.z;
		dest.w = lhs.w - rhs.w;
		
		dest.invalidateRotationMatrix();
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Subtract two Quaternions and return the result.
	 * 
	 * @param lhs
	 * @param rhs
	 * @return a new Quaternion which is the result of lhs subracted by rhs.
	 */
	public static Quaternion subtract(Quaternion lhs, Quaternion rhs) {
		Quaternion result = new Quaternion();
		subtract(lhs, rhs, result);
		
		return result;
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Multiply two Quaternions and store the result in <code>dest</code>.
	 * 
	 * @param lhs
	 * @param rhs
	 * @param dest
	 */
	public static void mult(Quaternion lhs, Quaternion rhs, Quaternion dest) {
        float x = (lhs.y * rhs.z) - (lhs.z * rhs.y) + (rhs.w * lhs.x) + (lhs.w * rhs.x);
        float y = (lhs.z * rhs.x) - (lhs.x * rhs.z) + (rhs.w * lhs.y) + (lhs.w * rhs.y);
        float z = (lhs.x * rhs.y) - (lhs.y * rhs.x) + (rhs.w * lhs.z) + (lhs.w * rhs.z);
        float w = (lhs.w * rhs.w) - (lhs.x * rhs.x) - (lhs.y * rhs.y) - (lhs.z * rhs.z);
        
        dest.x = x;
        dest.y = y;
        dest.z = z;
        dest.w = w;
		
		dest.invalidateRotationMatrix();
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Multiply two Quaternions and return a new Quaternion representing the
	 * result.
	 * 
	 * @param lhs
	 * @param rhs
	 * @return a new <code>Quanternion</code> representing <code>lhs</code>
	 *         multiplied by <code>rhs</code>.
	 */
	public static Quaternion mult(Quaternion lhs, Quaternion rhs) {
		Quaternion result = new Quaternion();
		mult(lhs, rhs, result);
		
		return result;
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Copy the components of <code>src</code> into <code>dest</code>.
	 * @param src
	 * @param dest
	 */
	public static void copy(Quaternion src, Quaternion dest) {
		dest.x = src.x;
		dest.y = src.y;
		dest.z = src.z;
		dest.w = src.w;
		
		dest.invalidateRotationMatrix();
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Conjugates this Quaternion.
	 * <br>
	 * Thus, given a Quaternion q = (v, w) with
	 * imaginary 3-component vector v, and scalar part w, q.conjugate() == (-v, w).
	 */
	public void conjugate() {
		x *= -1;
		y *= -1;
		z *= -1;
		
		invalidateRotationMatrix();
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Computes the conjugate of <code>src</code> and stores it in
	 * <code>dest</code>.
	 * 
	 * @param src
	 * @param dest
	 */
	public static void conjugate(Quaternion src, Quaternion dest) {
		copy(src, dest);
		dest.conjugate();
	}
	
	//--------------------------------------------------------------------------
	public static Quaternion conjugate(Quaternion q) {
		Quaternion result = new Quaternion();
		conjugate(q, result);
		
		return result;
	}
	
	//--------------------------------------------------------------------------
	/**
	 * @return the Euclidean norm (i.e. 2-norm) squared for this Quaternion.
	 */
	public float normSquared( ) {
		return x*x + y*y + z*z + w*w;
	}
	
	//--------------------------------------------------------------------------
	/**
	 * @return the Euclidean norm (i.e. 2-norm) for this Quaternion.
	 */
	public float norm() {
		return (float)Math.sqrt(normSquared());
	}
	
	
	//--------------------------------------------------------------------------
	public void normalize() {
		float s = 1f / norm();
		scale(s);
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Multiply each component of this Quaternion by s.
	 * @param s - scale factor.
	 */
	public void scale(float s) {
		x *= s;
		y *= s;
		z *= s;
		w *= s;
		
		invalidateRotationMatrix();
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Multiply each component of <code>src</code> by <code>s</code> and store
	 * in <code>dest</code>.
	 * 
	 * @param src - source Quaternion
	 * @param s	- scaling factor
	 * @param dest - destination Quaternion
	 */
	public static void scale(Quaternion src, float s, Quaternion dest) {
		Quaternion.copy(src, dest);
		dest.scale(s);
	}
	
	//--------------------------------------------------------------------------
	public static Quaternion scale(Quaternion src, float s) {
		Quaternion result = new Quaternion();
		scale(src, s, result);
		
		return result;
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Inverse this {@link Quaternion}.
	 */
	public void inverse() {
	    // If zero length, return in order to prevent a divide-by-zero.
	    if (normSquared() < MathUtils.EPSILON){
	        return;
	    }
		conjugate();
		scale(1f / normSquared());
		
		invalidateRotationMatrix();
	}
	
	
	//--------------------------------------------------------------------------
    /**
     * Compute the inverse of <code>src</code> and store the result in
     * <code>dest</code>.
     * 
     * @param src
     * @param dest
     */
	public static void inverse(Quaternion src, Quaternion dest) {
	    copy(src, dest);
	    dest.inverse();
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Computes the inverse of <code>src</code> and stores the result in newly
	 * allocated {@link Quaternion}, which is returned.
	 * 
	 * @param src - Quaternion whose inverse is to be returned.
	 * @return the inverse {@link Quaternion} of <code>src</code>.
	 */
	public static Quaternion inverse(Quaternion src) {
		Quaternion result = new Quaternion();
		inverse(src, result);
		
		return result;
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Creates a new {@link Matrix4f} and sets its elements to represent
	 * the rotation matrix for this {@link Quaternion}.
	 * 
	 * @return a rotation matrix representation of this <code>Quaternion</code>.
	 */
	public Matrix4f toRotationMatrix() {
		Matrix4f result = new Matrix4f();
		toRotationMatrix(result);
		
		return result;
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Stores the rotation matrix representation of this {@link Quaternion}
	 * in the {@link Matrix4f} dest.
	 * 
	 * @param dest - destination for storing the rotation matrix.
	 */
	public void toRotationMatrix(Matrix4f dest) {
		if (rotationMatrixNeedsUpdate) {
            float s = 2f / norm();

            dest.m00 = 1 - s * (y * y + z * z);
            dest.m01 = s * (x * y + w * z);
            dest.m02 = s * (x * z - w * y);

            dest.m10 = s * (x * y - w * z);
            dest.m11 = 1 - s * (x * x + z * z);
            dest.m12 = s * (y * z + w * x);

            dest.m20 = s * (x * z + w * y);
            dest.m21 = s * (y * z - w * x);
            dest.m22 = 1 - s * (x * x + y * y);
            
    		Matrix4f.load(dest, rotationMatrix);
    		rotationMatrixNeedsUpdate = false;
		}
		else {
		    Matrix4f.load(rotationMatrix, dest);
		}
	}
	
	//--------------------------------------------------------------------------
	private void invalidateRotationMatrix() {
	    rotationMatrixNeedsUpdate = true;
	}
	
	// --------------------------------------------------------------------------
    /**
     * Rotates <code>Quaternion q</code> using this <code>Quaternion</code>. Let
     * this <code>Quaternion</code> be p. The rotation of q to q' is equivalent
     * to: <br>
     * 
     * <pre>
     * q' = p*q*p^(-1)
     * </pre>
     * 
     * <br>
     * where * denotes <code>Quaternion</code> multiplication.
     * 
     * @param q - the Quaternion to be rotated.
     */
	public void rotate(Quaternion q) {
	    if (this == q) return;  // Rotating itself has no effect.
	    
		vRotTemp.x = q.x;
		vRotTemp.y = q.y;
		vRotTemp.z = q.z;
		vRotTemp.w = q.w;
		
		this.rotate(vRotTemp);
		
		q.x = vRotTemp.x;
		q.y = vRotTemp.y;
		q.z = vRotTemp.z;
		q.w = vRotTemp.w;
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Rotates <code>vec</code> using this
	 * {@link Quaternion}.
	 * 
	 * @param vec - the <code>Vector3f</code> to be rotated.
	 */
	public void rotate(Vector3f vec) {
		vRotTemp.x = vec.x;
		vRotTemp.y = vec.y;
		vRotTemp.z = vec.z;
		vRotTemp.w = 0f;
		
		this.rotate(vRotTemp);
		
		vec.x = vRotTemp.x;
		vec.y = vRotTemp.y;
		vec.z = vRotTemp.z;
	}
	
	//--------------------------------------------------------------------------
	/**
	 * Rotates <code>vec</code> using this
	 * {@link Quaternion}.
	 * 
	 * @param vec - the <code>Vector4f</code> to be rotated.
	 */
	public void rotate(Vector4f vec) {
		// Skip rotation by zero length Quaternion.
		if (this.normSquared() < MathUtils.EPSILON) {
			return;
		}
		
		if(rotationMatrixNeedsUpdate) {
		    rotationMatrix = this.toRotationMatrix();
		}
		
		Matrix4f.transform(rotationMatrix, vec, vec);
	}
	
	//--------------------------------------------------------------------------
	public void fromAxes(Vector3f xAxis, Vector3f yAxis, Vector3f zAxis) {
		Matrix4f rotationMatrix = new Matrix4f();
		
		rotationMatrix.m00 = xAxis.x;
		rotationMatrix.m01 = xAxis.y;
		rotationMatrix.m02 = xAxis.z;
		
		rotationMatrix.m10 = yAxis.x;
		rotationMatrix.m11 = yAxis.y;
		rotationMatrix.m12 = yAxis.z;
		
		rotationMatrix.m20 = zAxis.x;
		rotationMatrix.m21 = zAxis.y;
		rotationMatrix.m22 = zAxis.z;
		
		this.fromRotationMatrix(rotationMatrix);
	}
	
	//--------------------------------------------------------------------------
    /**
     * Modfies the elements of this {@link Quaternion} so that it represents the
     * rotation matrix <code>mat</code>. Assumes <code>mat</code> is an
     * orthonormal matrix.
     * 
     * @param mat - the rotation matrix
     */
    public void fromRotationMatrix(Matrix4f mat) {
	    /*
	     * Ken Shoemake's algorithm.
	     * This algorithm avoids near-zero divides by looking for a large component
	     * --first w, then x, y, or z. When the trace is greater than zero,
	     * |w| is greater than 1/2, which is as small as a largest component can be.
	     * Otherwise, the largest diagonal entry corresponds to the largest of |x|,
	     * |y|, or |z|, one of which must be larger than |w|, and at least 1/2.
	     * 
	     */
        float trace = mat.m00 + mat.m11 + mat.m22 + mat.m33;
        float s;
        
        if (trace >= 0.0f) {
            s = (float)Math.sqrt(trace);
            w = 0.5f * s;
            s = 1f / (4f*w);
            
            x = (mat.m12 - mat.m21) * s;
            y = (mat.m20 - mat.m02) * s;
            z = (mat.m01 - mat.m10) * s;
    	}
        else {
            // Map indices to values: 0->|x|, 1->|y|, 2->|z|.
            float matDiag[] = {mat.m11, mat.m22};
            int index = 0;
            
            // Determine the largest magnitude: |x|, |y|, or |z|.
            if (mat.m11 > mat.m00) index = 1;           // |y| > |x|
            if (mat.m22 > matDiag[index]) index = 2;    // |z| > |x| or |z| > |y|
            
            switch(index) {
            case 0:
                // |x| is largest.
                s = (float)Math.sqrt(mat.m00 - (mat.m11 + mat.m22) + mat.m33);
                x = s * 0.5f;
                w = (mat.m12 - mat.m21) / (4f * x);
                s = 1 / (4f * w);
                y = (mat.m20 - mat.m02) * s;
                z = (mat.m01 - mat.m10) * s;
                break;
                
            case 1:
                // |y| is largest.
                s = (float)Math.sqrt(mat.m11 - (mat.m00 + mat.m22) + mat.m33);
                y = s * 0.5f;
                w = (mat.m20 - mat.m02) / (4f * y);
                s = 1 / (4f * w);
                x = (mat.m12 - mat.m21) * s;
                z = (mat.m01 - mat.m10) * s;
                break;
                
            case 2:
                // |z| is largest.
                s = (float)Math.sqrt(mat.m22 - (mat.m00 + mat.m11) + mat.m33);
                z = s * 0.5f;
                w = (mat.m01 - mat.m10) / (4f * z);
                s = 1 / (4f * w);
                x = (mat.m12 - mat.m21) * s;
                y = (mat.m20 - mat.m02) * s;
                break;
            }
        }
        
        if (mat.m33 != 1.0f){
           s = (float)(1f / Math.sqrt(mat.m33));
           this.scale(s);
        }
        
        invalidateRotationMatrix();
	}

	//--------------------------------------------------------------------------
	private void validateVectorIsNonZero(Vector3f vec) {
		if (vec.lengthSquared() < MathUtils.EPSILON) {
			throw new IllegalArgumentException(
					"Vector3f parameter cannot have zero length.");
		}
	}
	
	
	
}
