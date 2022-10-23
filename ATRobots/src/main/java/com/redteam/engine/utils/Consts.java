package com.redteam.engine.utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Consts {

	public static final String TITLE = "ATRobotsV3D",
							   BORDER_HIT = "bloop_x.wav";
	
	public static final float FOV = (float) Math.toRadians(60),
			Z_NEAR = 0.01f,
			Z_FAR = 1000f,
			MOUSE_SENSITIVITY = 0.2f,
			SPECULAR_POWER = 10f,
			FPS = 1000;

	public static final int MAX_SPOT_LIGHTS = 5,
							MAX_POINT_LIGHTS = 5,
							X_BORDER = 75,
							Z_BORDER = X_BORDER * 2;

	public static final Vector4f DEFAULT_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Vector3f AMBIENT_LIGHT = new Vector3f(1.3f, 1.3f, 1.3f);
}