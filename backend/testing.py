import requests

class TestingRoutes:

    tests= []  
    url = ""

    def __init__(self, url):
        self.url = url
    
    def url_change(self, url):
        self.url = url

    def post(self, data, route_tested):
        response = requests.post(self.url, data)

        if(response.status_code==200):
            self.tests.append(route_tested + ": SUCCESS with status code : " + str(response.status_code))
        else:
            self.tests.append(route_tested + ": FAILURE with status code : " + str(response.status_code))
    
    def printer(self):

        print(self.tests)
    
    def get(self, route_tested):
        response = requests.get(self.url)
        print(route_tested + ": Output")
        print(response.content)

        if(response.status_code==200):
            print(route_tested + ": Output")
            print(response.content)
            self.tests.append(route_tested + ": SUCCESS with status code : " + str(response.status_code))
        else:
            self.tests.append(route_tested + ": FAILURE with status code : " + str(response.status_code))

obj = TestingRoutes("https://smart-copd-patient.herokuapp.com/login")
data = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijk5MWIwNjM2YWFkYTM0MWM1YTA4ZTBkOGYyNDA2OTcyMDY0ZGM4ZWQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiMzkxMzY0NjEwOTMzLWVmazdzMG41M2h2MDY3cDI1djMxZG92dTlkMjM2dnA3LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiMzkxMzY0NjEwOTMzLWVmazdzMG41M2h2MDY3cDI1djMxZG92dTlkMjM2dnA3LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTAxODY2NTI2MzY3Nzc2Mzk4MDYzIiwiaGQiOiJsZWhpZ2guZWR1IiwiZW1haWwiOiJhdGsyMjJAbGVoaWdoLmVkdSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiU1RWYUFHbWdEWVZEMXBMVFpuU3htQSIsIm5hbWUiOiJUaGFub3MgS291Z2lvbmlzIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hLS9BT2gxNEdoTUNGbTVIbmNhZnNDVlNMY1VfU1RoY0ZRY0lfUlhORm1HNFQtNT1zOTYtYyIsImdpdmVuX25hbWUiOiJUaGFub3MiLCJmYW1pbHlfbmFtZSI6IktvdWdpb25pcyIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNjMwOTY5MzY1LCJleHAiOjE2MzA5NzI5NjUsImp0aSI6ImUxODkwODlhZmQyNmRlYzNmNmM4ZDAwMGZkOTdkNjliZjEwNTYwZDIifQ.oR7ctCBl2W1g3Pi0smSQEtNssbUNMkYa00lCxDySBIhPum1Ly7PypyG_GBok5hMpTtlnegsqM25hyLvSEmAD27cXrHUjSNylHZiQy3xWXZ0pCHF5Jx_h_GKtarcNkClTBAgRekt8mtjKS1SZ2N7bHuyA-jzt9B5mHnRqaPI4fgAgfKXMxndbFKXKaRXltn6O60LHLtuEo52K4oP64JT3Ur9uHCKiKXLrg1V9N7ngDsMyhhHW2i-UA2bnp7A4ggx605xv43dm3DQVAgyX_3TT--hgDPrTAl5kgpHOtBUit5qZLJQL4gmBC0JoZ0P_gwhYUB1grCtVPHEfJ3S7hAeY6A"
obj.post(data, "Login")
obj.printer()
obj.url_change("https://smart-copd-patient.herokuapp.com/check/"+data)
obj.get("Check:")


