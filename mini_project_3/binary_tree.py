from mininet.topo import Topo
class MyTopo( Topo ):


    def __init__( self ):


        Topo.__init__( self )

        h1H = self.addHost('h1')
        h2H = self.addHost('h2')
        h3H = self.addHost('h3')
        h4H = self.addHost('h4')
        h5H = self.addHost('h5')
        s1S = self.addSwitch('s1')
        s2S = self.addSwitch('s2')
        s3S = self.addSwitch('s3')
        s4S = self.addSwitch('s4')
        s5S = self.addSwitch('s5')
        s6S = self.addSwitch('s6')

        # Add links
        self.addLink( h1H,s1S )
        self.addLink( s1S,h2H )
        self.addLink( s1S,s4S )
        self.addLink( s4S,s2S )
        self.addLink( s2S, h3H )
        self.addLink( s2S, h4H )
        self.addLink( s4S, s6S)
        self.addLink( s6S, s5S )
        self.addLink( s5S, s3S )
        self.addLink( s3S, h5H )


topos = { 'mytopo': ( lambda: MyTopo() ) }
