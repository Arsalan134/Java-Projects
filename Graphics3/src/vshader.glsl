in vec3 position;
in vec3 normal;

uniform mat4 modelview;
uniform mat4 proj;

uniform vec3 light[2];

uniform vec3 ambient;
uniform vec3 diffuse;
uniform vec3 specular;
uniform float shininess;

out vec3 color;

void main(void) {

    gl_Position = proj * modelview * vec4(position, 1.0);

    vec3 diffuseSum = vec3(0, 0, 0);
    vec3 specularSum = vec3(0, 0, 0);

    for (int i=0; i<2; i++) {
		vec3 pos = (modelview * vec4(position, 1.0)).xyz;

		vec3 L = normalize(light[i] - pos);
		vec3 E = normalize(-pos);
		vec3 H = normalize(L + E);
		vec3 N = normalize(modelview * vec4(normal, 0.0)).xyz;

		float ln = dot(L, N);
		if (ln > 0.0) {
			float s = pow(max(dot(N, H), 0.0), shininess);
			diffuseSum = diffuseSum + ln * diffuse;
			specularSum = specularSum + s * specular;
		}
    }

    color = ambient + diffuseSum + specularSum;
}
