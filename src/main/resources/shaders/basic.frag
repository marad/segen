#version 150 core

in vec4 color;

out vec4 result;

void main(void) {
    if (gl_FrontFacing) {
        result = color;
    } else {
        result = vec4(1, 1, 1, 1);
    }
}