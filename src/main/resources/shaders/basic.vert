#version 150 core

uniform mat4 viewMatrix, projMatrix;

in vec3 in_position;
in vec3 in_normal;
in vec4 in_color;

out vec4 pass_color;

void main(void) {
    pass_color = in_color;
    //pass_color = vec4(in_normal, 1);
    gl_Position = projMatrix * viewMatrix * vec4(in_position, 1.0);
    //gl_Position = viewMatrix * projMatrix * vec4(in_position, 1.0);
    //gl_Position = vec4(in_position, 1.0);
}
