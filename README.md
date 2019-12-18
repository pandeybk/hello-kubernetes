# This will open a web browser and you'll authenticate with your email, password
# and the second factor (which you of course should be using)
```
gcloud auth login
```

# And now generate the kubeconfig:
```
gcloud container clusters get-credentials my-cluster --zone us-central1-a --project my-project
```

# Check if it works:
```
kubectl get pods --all-namespaces
```