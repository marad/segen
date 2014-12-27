#version 150 core

uniform mat4 viewMatrix, projMatrix;

in vec3 position;
in vec4 in_color;

out vec4 color;

void main(void) {
    color = in_color;
    gl_Position = projMatrix * viewMatrix * vec4(position, 1.0);
    //gl_Position = viewMatrix * projMatrix * vec4(position, 1.0);
    //gl_Position = vec4(position, 1.0);
}
