#version 150 core

in vec4 pass_color;

out vec4 out_result;

void main(void) {
    //if (gl_FrontFacing) {
        out_result = pass_color;
    //} else {
    //    result = vec4(1, 1, 1, 1);
    //}
}