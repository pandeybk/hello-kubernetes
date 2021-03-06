package com.goglides.k8sexample;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import java.io.FileReader;
import java.io.IOException;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;


@RestController
public class HelloController {
  @RequestMapping("/")
  public String index() {
    Boolean kubeconfig=true;
    KubernetesClient client;

    if (kubeconfig) {
      Config config = new ConfigBuilder()
      .withMasterUrl("https://35.202.190.3")
      .build();
       client = new DefaultKubernetesClient(config);             
  } else {
     client = new DefaultKubernetesClient();
  }
    
    NamespaceList myNs = client.namespaces().list();
    System.out.println(myNs);
    return "Greetings from Spring Boot!";
  }
  

  @RequestMapping("/")
  public String index2() {
    // file path to your KubeConfig
    String kubeConfigPath = "/Users/bkpandey/.kube/config";

    try {
    // loading the out-of-cluster config, a kubeconfig from file-system
    ApiClient client =
    ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();

    // set the global default api-client to the in-cluster one from above
    Configuration.setDefaultApiClient(client);

    // the CoreV1Api loads default api-client from global configuration.
    CoreV1Api api = new CoreV1Api();

    // invokes the CoreV1Api client
    V1PodList list =
    api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
    for (V1Pod item : list.getItems()) {
    System.out.println(item.getMetadata().getName());
}
    } catch (Exception e) {
        System.out.println(e);
    }
    
    return "Greetings from Spring Boot!";
  }

}