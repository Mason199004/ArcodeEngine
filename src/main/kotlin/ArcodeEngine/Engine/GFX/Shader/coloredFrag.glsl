#version 300 es

in mediump vec3 color;

out mediump vec4 out_Color;

void main(void) {
    out_Color = vec4(color, 0);
}
